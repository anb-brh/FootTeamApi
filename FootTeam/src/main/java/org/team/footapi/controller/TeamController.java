package org.team.footapi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.team.footapi.model.Team;
import org.team.footapi.service.TeamService;

/**
 * Rest Controller to generate the HTTP requests
 */
@RestController
@RequestMapping("/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    /**
     * HTTP Request to fetch all teams based on specific params
     * @param  page page parameter
     * @param size size parameter
     * @param sortBy sorting parameter
     * @return list of teams
     */
    @GetMapping
    public Page<Team> getAllTeams(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "name") String sortBy) {
        return teamService.getAllTeams(page, size, sortBy);
    }

    /**
     * HTTP Request to save a team
     * @param team team request body
     * @return team object
     */
    @PostMapping
    public Team saveTeam(@RequestBody Team team) {
        return teamService.saveTeam(team);
    }

    /**
     * HTTP Request to find a team by Id
     * @param id id of Team
     * @return  team object
     */
    @GetMapping("/{id}")
    public Team getTeamById(@PathVariable Long id) {
        return teamService.getTeamById(id);
    }

    /**
     * HTTP Request to update a team by Id
     * @param id id of Team
     * @param teamDetails details of Team
     * @return team object
     */
    @PutMapping("/{id}")
    public Team updateTeam(@PathVariable Long id, @RequestBody Team teamDetails) {
        return teamService.updateTeamById(id, teamDetails);
    }

    /**
     * HTTP Request to delete a team by Id
     * @param id id of Team
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTeam(@PathVariable Long id) {
        teamService.deleteTeamById(id);
    }
}