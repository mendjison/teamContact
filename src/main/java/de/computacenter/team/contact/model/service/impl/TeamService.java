package de.computacenter.team.contact.model.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import de.computacenter.team.contact.model.entities.Team;
import de.computacenter.team.contact.model.repository.TeamRepository;
import de.computacenter.team.contact.model.service.ErrorConstantValue;
import de.computacenter.team.contact.model.service.ITeamService;

@Service("TeamService")
public class TeamService implements ITeamService{

	private TeamRepository teamRepository;
	private static final Logger logger = LogManager.getLogger(ContactService.class);


	public TeamService(TeamRepository teamRepository) {
		super();
		this.teamRepository = teamRepository;
	}

	@Override
	public List<Team> findTeams() {

		return teamRepository.findAll();
	}
	
	@Override
	public Team findByTeamId(String teamId) {
		return teamRepository.findById(teamId).orElse(null);
	}

	@Override
	public Team findTeamByName(String teamName) {
		return teamRepository.findTeamByTeamName(teamName).orElse(null);
	}

	@Override
	public Team saveTeam(Team team) {

		validation(team);
		logger.info("TeamService.saveTeam: save-team is valid");

		teamAlreadyExist(team);
		logger.info("TeamService.saveTeam: save-team not exist");

		if(team.getTeamId() == null) {
			team.setTeamId(UUID.randomUUID().toString());
		}
		team = teamRepository.save(team);
		logger.info("TeamService.saveTeam: save team successful");
		return team;
	}

	@Override
	public Team updateTeam(String teamId, Team team) {
		
		checkTeamExistance( teamId);
		logger.info("TeamService.updateTeam: update team exist");
		
		validation(team);
		logger.info("TeamService.updateTeam: update-team is valid");

		teamAlreadyExist(team);
		logger.info("TeamService.updateTeam: team's name not exist");
		
		team.setTeamId(teamId);

		team = teamRepository.save(team);
		logger.info("TeamService.updateTeam: update team successful");
		return team;
	}

	@Override
	public void deleteteam(String teamId) {
		checkTeamExistance(teamId);
		teamRepository.delete(teamRepository.findById(teamId).get());
		logger.info("TeamService.deleteteam: delete team successful");

	}

	private void validation(Team team) {
		if(team == null) {
			logger.error("TeamService.validation: " + ErrorConstantValue.TEAM_NOT_NULL);
			throw new RuntimeException(ErrorConstantValue.TEAM_NOT_NULL);
		}
			

		if(team.getTeamName() ==null || team.getTeamName().equals("")) {
			logger.error("TeamService.validation: " + ErrorConstantValue.TEAMNAME_NOT_NULL);
			throw new RuntimeException(ErrorConstantValue.TEAMNAME_NOT_NULL);
		}
			
	}

	private void teamAlreadyExist(Team team) {
		Optional<Team> optionalTeam = teamRepository
				.findTeamByTeamName(team.getTeamName());
		if(optionalTeam.isPresent()) {
			logger.error("TeamService.teamAlreadyExist: " + ErrorConstantValue.TEAM_ALREADY_EXIST);
			throw new RuntimeException(ErrorConstantValue.TEAM_ALREADY_EXIST);
		}
			
	}
	
	private void checkTeamExistance(String teamId) {
		
		if (teamId == null 
				|| teamId.equals("")
				|| !teamRepository.findById(teamId).isPresent()) {
			logger.error("TeamService.checkTeamExistance: " + ErrorConstantValue.TEAM_NOT_EXIST);
			throw new RuntimeException(ErrorConstantValue.TEAM_NOT_EXIST);
		}
			
	}

}
