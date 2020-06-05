package de.computacenter.team.contact.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import de.computacenter.team.contact.model.entities.Team;

public interface TeamRepository extends JpaRepository<Team, String>{

	Optional<Team> findTeamByTeamName(String teamName);
}
