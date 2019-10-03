package gizmo.business.incident.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gizmo.core.enums.Action;


@Entity
@Table(name="incident_history")
public class IncidentHistory {

	@Id
  @GeneratedValue
  private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="incident_id")
	private Incident incident;
	
	private Action action;
	
	@Temporal (TemporalType.TIMESTAMP)
  @Column(name="modified_date", updatable=false)
  private Date modifiedDate;
	
	public IncidentHistory() {}
	
	public IncidentHistory(Incident incident, Action action) {
		this.incident = incident;
		this.action = action;
		this.modifiedDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Incident getIncident() {
		return incident;
	}

	public void setIncident(Incident incident) {
		this.incident = incident;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}
	
}