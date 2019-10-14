package gizmo.business.incident.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


// NOTE: there is no need for this to be a managed entity; only a DTO between layers is needed

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class IncidentKeyword {

	private String keywordName;
	private String incidentName;
	private String description;
	private String solution;
	
	public IncidentKeyword(String keywordName, String incidentName, String description, String solution) {
		this.keywordName = keywordName;
		this.incidentName = incidentName;
		this.description = description;
		this.solution = solution;
	}
	
	public String getKeywordName() {
		return keywordName;
	}
	
	public void setKeywordName(String keywordName) {
		this.keywordName = keywordName;
	}
	
	public String getIncidentName() {
		return incidentName;
	}
	
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSolution() {
		return solution;
	}
	
	public void setSolution(String solution) {
		this.solution = solution;
	}
	
}