package de.computacenter.team.contact.model.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Entity
public class Team implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	private String teamId;
	@Column(length = 30, unique = true, nullable = false)
	private String teamName;
	
	public Team() {
		super();
	}
	
	
	public Team(String teamId, String teamName) {
		super();
		this.teamId = teamId;
		this.teamName = teamName;
	}


	public Team(String teamName) {
		this(UUID.randomUUID().toString(), teamName);
	}
	
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}
	public String getTeamName() {
		return teamName;
	}
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
