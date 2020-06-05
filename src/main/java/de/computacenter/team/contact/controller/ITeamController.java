package de.computacenter.team.contact.controller;

import java.util.List;

import de.computacenter.team.contact.model.entities.Team;

public interface ITeamController {

	List<Team> findTeams();
	Team findByTeamName(String teamName);
	Team saveTeam(Team team);
	Team updateTeam(String teamId, Team team);
	void deleteteam(String teamId);
	Team findByTeamId(String teamId);
}
