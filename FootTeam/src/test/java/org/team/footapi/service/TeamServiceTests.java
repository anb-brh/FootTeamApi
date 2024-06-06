package org.team.footapi.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.team.footapi.exception.ResourceNotFoundException;
import org.team.footapi.model.Team;
import org.team.footapi.repository.TeamRepository;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeamServiceTests {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method to test getAllTeams
     */
    @Test
    void getAllTeamsTest() {
        Page<Team> teams = new PageImpl<>(Collections.singletonList(new Team()));
        when(teamRepository.findAll(PageRequest.of(0, 10, Sort.by("name")))).thenReturn(teams);

        Page<Team> result = teamService.getAllTeams(0, 10, "name");

        assertEquals(1, result.getTotalElements());
        verify(teamRepository, times(1)).findAll(PageRequest.of(0, 10, Sort.by("name")));
    }

    /**
     * Method to test saveTeamTest
     */
    @Test
    void saveTeamTest() {
        Team team = new Team();
        team.setName("Nice");
        team.setAcronym("N");
        team.setBudget(45000000);

        when(teamRepository.save(any(Team.class))).thenReturn(team);

        Team result = teamService.saveTeam(team);

        assertNotNull(result);
        assertEquals("Nice", result.getName());
        verify(teamRepository, times(1)).save(team);
    }


    /**
     * Method to test getTeamById
     */
    @Test
    void getTeamByIdTest() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        Team result = teamService.getTeamById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(teamRepository, times(1)).findById(1L);
    }

    /**
     * Method to test getTeamByIdNotFoundTest
     */
    @Test
    void getTeamByIdNotFoundTest() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            teamService.getTeamById(1L);
        });

        verify(teamRepository, times(1)).findById(1L);
    }

    /**
     * Method to test updateTeamTest
     */
    @Test
    void updateTeamTest() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");

        Team updatedTeamDetails = new Team();
        updatedTeamDetails.setName("Updated Nice");
        updatedTeamDetails.setAcronym("UN");
        updatedTeamDetails.setBudget(80000000);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));
        when(teamRepository.save(any(Team.class))).thenReturn(updatedTeamDetails);

        Team result = teamService.updateTeamById(1L, updatedTeamDetails);

        assertNotNull(result);
        assertEquals("Updated Nice", result.getName());
        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).save(any(Team.class));
    }

    /**
     * Method to test updateTeamNotFoundTest
     */
    @Test
    void updateTeamNotFoundTest() {
        Team updatedTeamDetails = new Team();
        updatedTeamDetails.setName("Updated Nice");
        updatedTeamDetails.setAcronym("UN");
        updatedTeamDetails.setBudget(100000000);

        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            teamService.updateTeamById(1L, updatedTeamDetails);
        });

        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(0)).save(any(Team.class));
    }

    /**
     * Method to test deleteTeamTest
     */
    @Test
    void deleteTeamTest() {
        Team team = new Team();
        team.setId(1L);
        team.setName("Nice");

        when(teamRepository.findById(anyLong())).thenReturn(Optional.of(team));

        teamService.deleteTeamById(1L);

        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(1)).delete(team);
    }

    /**
     * Method to test deleteTeamNotFoundTest
     */
    @Test
    void deleteTeamNotFoundTest() {
        when(teamRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            teamService.deleteTeamById(1L);
        });

        verify(teamRepository, times(1)).findById(1L);
        verify(teamRepository, times(0)).delete(any(Team.class));
    }
}
