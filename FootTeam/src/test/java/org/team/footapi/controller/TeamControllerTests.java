package org.team.footapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.team.footapi.exception.ResourceNotFoundException;
import org.team.footapi.model.Team;
import org.team.footapi.service.TeamService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(TeamController.class)
public class TeamControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    /**
     * Method to test getAllTeams
     */
    @Test
    void getAllTeamsTest() throws Exception {
        when(teamService.getAllTeams(0, 10, "name")).thenReturn(Page.empty());

        mockMvc.perform(get("/teams")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    /**
     * Method to test saveTeam
     */
    @Test
    void saveTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");
        team.setAcronym("N");
        team.setBudget(25000000);

        when(teamService.saveTeam(any(Team.class))).thenReturn(team);

        mockMvc.perform(post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Nice\", \"acronym\": \"N\", \"budget\": 25000000 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nice"))
                .andExpect(jsonPath("$.acronym").value("N"))
                .andExpect(jsonPath("$.budget").value(25000000.0));
    }

    /**
     * Method to test getTeamByIdTest
     */
    @Test
    void getTeamByIdTest() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");
        team.setAcronym("N");
        team.setBudget(25000000);

        when(teamService.getTeamById(1L)).thenReturn(team);

        mockMvc.perform(get("/teams/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nice"))
                .andExpect(jsonPath("$.acronym").value("N"))
                .andExpect(jsonPath("$.budget").value(25000000.0));
    }

    /**
     * Method to test getTeamByIdNotFoundTest
     */
    @Test
    void getTeamByIdNotFoundTest() throws Exception {
        when(teamService.getTeamById(2L)).thenThrow(new ResourceNotFoundException("Team not found with id 2"));

        mockMvc.perform(get("/teams/{id}", 2L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Team not found with id 2"))
                .andExpect(jsonPath("$.details").value("Resource not found"));
    }

    /**
     * Method to test updateTeamByIdTest
     */
    @Test
    void updateTeamTest() throws Exception {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");
        team.setAcronym("N");
        team.setBudget(45000000);

        when(teamService.updateTeamById(anyLong(), any(Team.class))).thenReturn(team);

        mockMvc.perform(put("/teams/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"name\": \"Nice\", \"acronym\": \"N\", \"budget\": 45000000 }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Nice"))
                .andExpect(jsonPath("$.acronym").value("N"))
                .andExpect(jsonPath("$.budget").value(45000000.0));
    }

    /**
     * Method to test updateTeamByIdNotFoundTest
     */
    @Test
    void updateTeamByIdNotFoundTest() throws Exception {
        when(teamService.getTeamById(2L)).thenThrow(new ResourceNotFoundException("Team not found with id 2"));

        mockMvc.perform(get("/teams/{id}", 2L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Team not found with id 2"))
                .andExpect(jsonPath("$.details").value("Resource not found"));
    }

    /**
     * Method to test deleteTeamByIdTest
     */
    @Test
    void deleteTeamByIdTest() throws Exception {
        mockMvc.perform(delete("/teams/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    /**
     * Method to test deleteTeamByIdTest
     */
    @Test
    void deleteTeamByIdNotFoundTest() throws Exception {
        when(teamService.getTeamById(2L)).thenThrow(new ResourceNotFoundException("Team not found with id 2"));
        mockMvc.perform(delete("/teams/{id}", 1L))
                .andExpect(status().isNoContent());
    }
}
