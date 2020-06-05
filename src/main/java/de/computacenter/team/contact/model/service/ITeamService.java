package de.computacenter.team.contact.model.service;

import java.util.List;
import de.computacenter.team.contact.model.entities.Team;

public interface ITeamService {

	List<Team> findTeams();
	Team findByTeamId(String teamId);
	Team findTeamByName(String teamName);
	Team saveTeam(Team team);
	Team updateTeam(String teamId, Team team);
	void deleteteam(String teamId);
}
