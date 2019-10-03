package gizmo.business.incident.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Composite {

	private Incident incident;
	private int[] keywordIds;
	
	public Incident getIncident() {
		return incident;
	}
	
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	public int[] getKeywordIds() {
		return keywordIds;
	}
	
	public void setKeywordIds(int[] keywordIds) {
		this.keywordIds = keywordIds;
	}
	
}