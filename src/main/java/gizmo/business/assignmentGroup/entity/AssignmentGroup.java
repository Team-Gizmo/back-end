package gizmo.business.assignmentGroup.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import gizmo.business.assignmentGroup.control.AssignmentGroupEntityListener;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@EntityListeners(AssignmentGroupEntityListener.class)
public class AssignmentGroup {

	@Id
	@GeneratedValue
  private Long id;
	
	@NotNull
  private String name;
	
	@Column(name="create_date", updatable=false, nullable=false)
  private LocalDateTime createDate;

	public AssignmentGroup() {
		this.createDate = LocalDateTime.now();
	}
	
	public AssignmentGroup(@NotNull String name) {
		this.name = name;
		this.createDate = LocalDateTime.now();
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

	public LocalDateTime getCreateDate() {
		return createDate;
	}
	
}