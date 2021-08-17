package io.example.springdatajpa.repository.ch03_team_crud;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Team;
import io.example.springdatajpa.generator.TeamGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : choi-ys
 * @date : 2021/04/05 7:40 오후
 * @Content : Spring Data Jpa Repository Test
 */
@DisplayName("CrudRepository[SpringDataJpa]:Team")
class TeamCrudRepositorySpringDataJpaTest extends BaseTest {

    @Resource
    TeamCrudRepositorySpringDataJpa teamCrudRepositorySpringDataJpa;

    @Test
    @DisplayName("Save")
    //    @Rollback(value = false)
    public void saveTeam(){
        // Given
        Team team = TeamGenerator.createTeam();

        // When
        Team savedTeam = this.teamCrudRepositorySpringDataJpa.save(team);

        // Then
        assertEquals(savedTeam, team);
        assertEquals(savedTeam.getNo(), team.getNo());
        assertEquals(savedTeam.getName(), team.getName());
    }

    @Test
    @DisplayName("FindById")
    //    @Rollback(value = false)
    public void findTeamById(){
        // Given
        Team team = TeamGenerator.createTeam();
        this.teamCrudRepositorySpringDataJpa.save(team);

        // When
        Optional<Team> optionalTeam = teamCrudRepositorySpringDataJpa.findById(team.getNo());
        Team selectedTeam = optionalTeam.orElseThrow();

        // Then
        assertEquals(selectedTeam, team);
    }

    @Test
    @DisplayName("Update")
    //    @Rollback(value = false)
    public void updateTeam(){
        // Given
        Team team = TeamGenerator.createTeam();
        Team savedTeam = this.teamCrudRepositorySpringDataJpa.save(team);

        // When
        String changedMemberName = "BigDataTeam";
        team.changeTeamName(changedMemberName);

        // Then
        assertEquals(savedTeam, team);
        assertEquals(team.getName(), changedMemberName);
    }

    @Test
    @DisplayName("Delete")
    //    @Rollback(value = false)
    public void deleteTeam(){
        // Given
        Team team = TeamGenerator.createTeam();
        Team savedTeam = this.teamCrudRepositorySpringDataJpa.save(team);
        assertEquals(savedTeam, team);

        // When
        this.teamCrudRepositorySpringDataJpa.delete(team);

        // Then
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> {
            this.teamCrudRepositorySpringDataJpa.findById(team.getNo()).orElseThrow();
        });
        assertEquals(noSuchElementException.getMessage(), "No value present");
    }

    @Test
    @DisplayName("Count")
    //    @Rollback(value = false)
    public void teamCount(){
        // Given
        Team firstTeam = TeamGenerator.createTeamByTeamName("CoreDevTeam");
        Team secondTeam = TeamGenerator.createTeamByTeamName("BigDataTeam");
        Team thirdTeam = TeamGenerator.createTeamByTeamName("AirDevTeam");
        this.teamCrudRepositorySpringDataJpa.save(firstTeam);
        this.teamCrudRepositorySpringDataJpa.save(secondTeam);
        this.teamCrudRepositorySpringDataJpa.save(thirdTeam);

        // When
        long teamCount = this.teamCrudRepositorySpringDataJpa.count();

        // Then
        assertThat(teamCount).isNotZero();
        assertEquals(teamCount, 3);
    }

    @Test
    @DisplayName("FindAll")
    public void findAllTeam(){
        // Given
        Team firstTeam = TeamGenerator.createTeamByTeamName("CoreDevTeam");
        Team secondTeam = TeamGenerator.createTeamByTeamName("BigDataTeam");
        Team thirdTeam = TeamGenerator.createTeamByTeamName("AirDevTeam");
        this.teamCrudRepositorySpringDataJpa.save(firstTeam);
        this.teamCrudRepositorySpringDataJpa.save(secondTeam);
        this.teamCrudRepositorySpringDataJpa.save(thirdTeam);

        // When
        List<Team> savedTeamList = this.teamCrudRepositorySpringDataJpa.findAll();

        // Then
        assertThat(savedTeamList.size()).isNotZero();
        assertThat(savedTeamList.contains(firstTeam));
        assertThat(savedTeamList.contains(secondTeam));
        assertThat(savedTeamList.contains(thirdTeam));
    }
}
