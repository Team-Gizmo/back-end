package gizmo.business.incident.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import gizmo.business.incident.control.IncidentEntityListener;
import gizmo.business.keyword.entity.Keyword;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@EntityListeners(IncidentEntityListener.class)
public class Incident {

	@Id
  @GeneratedValue
  private Long id;
	
	@NotNull
  private String name;
	
	@NotNull
	private String description;
	
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinTable(
			name = "Incident_Keyword", 
			joinColumns = @JoinColumn(name="incident_id", referencedColumnName="id"), 
			inverseJoinColumns = @JoinColumn(name="keyword_id", referencedColumnName="id")
	)
	private Collection<Keyword> keywords;
	
	private String solution;
	
	@Version
  @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
  private long version = 0L;
	
	@Column(name="create_date", updatable=false, nullable=false)
  private LocalDateTime createDate;
	
	@Column(name="resolve_date", updatable=false, nullable=false)
  private LocalDateTime resolveDate;

	public Incident() {
		this.createDate = LocalDateTime.now();
	}
	
	public Incident(@NotNull String name, @NotNull String description) {
		this.name = name;
		this.description = description;
		this.createDate = LocalDateTime.now();
	}

	public Incident(@NotNull String name, @NotNull String description, String solution) {
		this.name = name;
		this.description = description;
		this.solution = solution;
	}
	
	public Incident(Long id, String name, String description, String solution) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.solution = solution;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Collection<Keyword> getKeywords() {
		return new ArrayList<Keyword>(keywords);
	}

	public void setKeywords(Collection<Keyword> keywords) {
		this.keywords = keywords;
	}
	
	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public Long getVersion() {
		return version;
	}
	
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getResolveDate() {
		return resolveDate;
	}

	public void setResolveDate(LocalDateTime resolveDate) {
		this.resolveDate = resolveDate;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Incident [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (name != null)
			builder.append("name=").append(name).append(", ");
		if (description != null)
			builder.append("description=").append(description).append(", ");
		if (keywords != null)
			builder.append("keywords=").append(keywords).append(", ");
		if (solution != null)
			builder.append("solution=").append(solution).append(", ");
		builder.append("version=").append(version).append(", ");
		if (createDate != null)
			builder.append("createDate=").append(createDate).append(", ");
		if (resolveDate != null)
			builder.append("resolveDate=").append(resolveDate);
		builder.append("]");
		return builder.toString();
	}
	
}