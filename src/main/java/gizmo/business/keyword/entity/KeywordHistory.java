package gizmo.business.keyword.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gizmo.core.enums.Action;


@Entity
@Table(name="keyword_history")
public class KeywordHistory {

	@Id
  @GeneratedValue
  private Long id;
	
	@ManyToOne
  @JoinColumn(name="keyword_id")
	private Keyword keyword;
	
	private Action action;
	
	@Column(name="modified_date", updatable=false, nullable=false)
  private LocalDateTime modifiedDate;
	
	public KeywordHistory() {}
	
	public KeywordHistory(Keyword keyword, Action action) {
		this.keyword = keyword;
		this.action = action;
		this.modifiedDate = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public Action getAction() {
		return action;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	
}