package gizmo.business.keyword.entity;

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
@Table(name="keyword_history")
public class KeywordHistory {

	@Id
  @GeneratedValue
  private Long id;
	
	@ManyToOne(fetch=FetchType.EAGER)
  @JoinColumn(name="keyword_id")
	private Keyword keyword;
	
	private Action action;
	
	@Temporal (TemporalType.TIMESTAMP)
  @Column(name="modified_date", updatable=false)
  private Date modifiedDate;
	
	public KeywordHistory() {}
	
	public KeywordHistory(Keyword keyword, Action action) {
		this.keyword = keyword;
		this.action = action;
		this.modifiedDate = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
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