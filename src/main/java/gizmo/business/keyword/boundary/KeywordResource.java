package gizmo.business.keyword.boundary;

import java.net.URI;
import java.util.Collection;

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

import org.apache.log4j.Logger;

import gizmo.business.keyword.entity.Keyword;


@Stateless
@Path("keywords")
public class KeywordResource {

	private static final Logger LOG = Logger.getLogger(KeywordResource.class);
	
	@Inject
	private KeywordManager manager; 
  
	@GET
	@Path("all")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Keyword> getAllKeywords() {
		return manager.getAllKeywords();
	}
	
	@GET
	@Path("doesKeywordExist")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean doesKeywordExist(@QueryParam("name") String s) {
		return manager.doesKeywordExist(s);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response createNewKeyword(Keyword request, @Context UriInfo info) {
		Response.ResponseBuilder builder = null;
		if (manager.doesKeywordExist(request.getName())) {		
			JsonObject conf = Json.createObjectBuilder().
					add("Keyword already exists", request.getName()).
					build();
			builder = Response.status(Response.Status.CONFLICT).entity(conf);
		}
		else {
			Keyword keyword = manager.createNewKeyword(request);
			long id = keyword.getId();
			URI uri = info.getAbsolutePathBuilder().path("/"+id).build();
			JsonObject conf = Json.createObjectBuilder().
				add("confirmation-id", id).
				add("name", keyword.getName()).
				build();
			builder = Response.created(uri).entity(conf);
		}
		return builder.build();
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateKeyword(@PathParam("id") long keywordId, Keyword request, @Context UriInfo info) {
		Response.ResponseBuilder builder = null;
		Keyword keyword = manager.update(keywordId, request);
		if (keyword != null) {
			URI uri = info.getAbsolutePathBuilder().path("/"+keywordId).build();
			JsonObject conf = Json.createObjectBuilder().
				add("confirmation-id", keywordId).
				add("name", keyword.getName()).
				build();
			builder = Response.created(uri).entity(conf);
		}
		else {
			JsonObject conf = Json.createObjectBuilder().
					add("Could not find Keyword", keywordId).
					build();
			builder = Response.status(Response.Status.NOT_FOUND).entity(conf);
		}
		return builder.build();
	}
	
	
}