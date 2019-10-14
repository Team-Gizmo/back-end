package gizmo.business.incident.boundary;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import gizmo.business.incident.entity.Composite;
import gizmo.business.incident.entity.Incident;
import gizmo.business.incident.entity.IncidentKeyword;



@Stateless
@Path("incidents")
public class IncidentsResource {
	
	private static final Logger LOG = Logger.getLogger(IncidentsResource.class);
	
	@Inject
	private IncidentsManager manager; 
  
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Incident> getAll() {
		return manager.getAllIncidents();
	}
		
	@GET
	@Path("search/keyword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Incident> getIncidentsByKeywordIds(@QueryParam("0") String sA, @QueryParam("1") String sB) {
		List<String> input = Arrays.asList(sA,sB);
		List<Long> values = input
				.stream()
				.filter(e -> StringUtils.isNotBlank(e))
				.map(Long::valueOf)
				.collect(Collectors.toList());
		return manager.getIncidentsByKeywordIds(values);
	}
	
	@GET
	@Path("incidentsAndKeywords/all")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<IncidentKeyword> getIncidentsAndKeywords() {
		return manager.getIncidentsAndKeywords();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewIncident(Composite request, @Context UriInfo info) {
		Response.ResponseBuilder builder = null;
		if (manager.doesIncidentExist(request.getIncident().getName())) {		
			JsonObject conf = Json.createObjectBuilder().
					add("Incident already exists", request.getIncident().getName()).
					build();
			builder = Response.status(Response.Status.CONFLICT).entity(conf);
		}
		else {
			Incident incident = manager.createNewIncident(request);
			long id = incident.getId();
			URI uri = info.getAbsolutePathBuilder().path("/"+id).build();
			JsonObject conf = Json.createObjectBuilder().
				add("confirmation-id", id).
				add("name", incident.getName()).
				build();
			builder = Response.created(uri).entity(conf);
		}
		return builder.build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("update/{id}")
	public Response updateIncident(@PathParam("id") long incidentId, Incident request, @Context UriInfo info) {
		Response.ResponseBuilder builder = null;
		Incident incident = manager.updateAll(incidentId, request);
		if (incident != null) {
			URI uri = info
					.getAbsolutePathBuilder()
					.path("/"+incidentId)
					.build();
			JsonObject conf = Json
					.createObjectBuilder()
					.add("confirmation-id", incidentId)
					.add("name", incident.getName())
					.build();
			builder = Response
					.created(uri)
					.entity(conf);
		}
		else {
			JsonObject conf = Json.createObjectBuilder()
					.add("Could not find Incident", incidentId)
					.build();
			builder = Response
					.status(Response.Status.NOT_FOUND)
					.entity(conf);
		}
		return builder.build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("solution/{id}")
	public Response updateSolution(@PathParam("id") long incidentId, Incident request, @Context UriInfo info) {
		Response.ResponseBuilder builder = null;
		Incident incident = manager.updateSolution(incidentId, request);
		if (incident != null) {
			URI uri = info
					.getAbsolutePathBuilder()
					.path("/"+incidentId)
					.build();
			JsonObject conf = Json.createObjectBuilder()
					.add("confirmation-id", incidentId)
					.add("name", incident.getName())
					.build();
			builder = Response
					.created(uri)
					.entity(conf);
		}
		else {
			JsonObject conf = Json
					.createObjectBuilder()
					.add("Could not find Incident", incidentId)
					.build();
			builder = Response
					.status(Response.Status.NOT_FOUND)
					.entity(conf);
		}
		return builder.build();
	}
	
}