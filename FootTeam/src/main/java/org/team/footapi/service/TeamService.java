package org.team.footapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.team.footapi.exception.ResourceNotFoundException;
import org.team.footapi.model.Team;
import org.team.footapi.repository.TeamRepository;

/**
 * Service class to define team operations
 */
@Service
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    /**
     * Method to return a list of teams containing their respective list of players
     * @param page page parameter
     * @param size size parameter
     * @param sortBy sorting parameter
     * @return list of teams
     */
    public Page<Team> getAllTeams(int page, int size, String sortBy) {
        return teamRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    /**
     * Method to add a new Team
     * @param newTeam new team parameter
     * @return team object
     */
    public Team saveTeam(Team newTeam) {
        return teamRepository.save(newTeam);
    }

    /**
     * Method to find a Team By Id
     * @param id id of the team
     * @return team object
     */
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));
    }

    /**
     * Method to update a Team By Id
     * @param id id of the team
     * @param teamDetails team details
     * @return team object
     */
    public Team updateTeamById(Long id, Team teamDetails) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));

        team.setName(teamDetails.getName());
        team.setAcronym(teamDetails.getAcronym());
        team.setBudget(teamDetails.getBudget());

        return teamRepository.save(team);
    }

    /**
     * Method to delete a Team By Id
     * @param id id of the team
     */
    public void deleteTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id " + id));

        teamRepository.delete(team);
    }
}
