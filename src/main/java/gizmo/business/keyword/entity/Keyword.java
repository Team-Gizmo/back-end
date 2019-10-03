package gizmo.business.keyword.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import gizmo.business.keyword.control.KeywordEntityListener;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"name"})})
@EntityListeners(KeywordEntityListener.class)
public class Keyword {

	@Id
	@GeneratedValue
  private Long id;
	
	@NotNull
  private String name;
	
	@Temporal (TemporalType.TIMESTAMP)
  @Column(name="create_date", updatable=false)
  private Date createDate;
	
	public Keyword() {
		this.createDate = new Date();
	}

	public Keyword(String name) {
		this.name = name;
		this.createDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateDate() {
		return createDate;
	}
	
}