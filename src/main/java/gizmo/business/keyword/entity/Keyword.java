package gizmo.business.keyword.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import gizmo.business.keyword.control.KeywordEntityListener;


@Entity
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@EntityListeners(KeywordEntityListener.class)
public class Keyword {

	@Id
	@GeneratedValue
  private Long id;
	
	@NotNull
  private String name;
	
	@Column(name="create_date", updatable=false, nullable=false)
  private LocalDateTime createDate;
	
	public Keyword() {
		this.createDate = LocalDateTime.now();
	}

	public Keyword(@NotNull String name) {
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