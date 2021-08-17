package io.example.springdatajpa.repository.ch04_team_member_join;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.domain.entity.Team;
import io.example.springdatajpa.generator.MemberGenerator;
import io.example.springdatajpa.generator.TeamGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author : choi-ys
 * @date : 2021/04/06 11:47 오전
 * @Content : Spring Data Jpa를 이용한 Team과 Member 연관관계의 Join 쿼리 Test
 */
@DisplayName("JoinRepository[QueryMethod]:TeamMember")
@Import({TeamGenerator.class, MemberGenerator.class})
class TeamMemberJoinRepositoryQueryMethodTest extends BaseTest {

    @Resource
    TeamGenerator teamGenerator;

    @Resource
    MemberGenerator memberGenerator;

    @Resource
    TeamMemberJoinRepositoryQueryMethod teamMemberJoinRepositoryQueryMethod;

    @Resource
    EntityManager entityManager;

    private void clearUp() {
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("특정 팀에 소속된 회원 목록 조회")
    public void findMemberListByTeamNo(){
        // Given
        Team savedTeam = teamGenerator.savedTeam();
        Member savedFirstMember = memberGenerator.savedMemberWithTeam("최용석", 31, savedTeam);
        Member savedSecondMember = memberGenerator.savedMemberWithTeam("이성욱", 31, savedTeam);
        Member savedThirdMember = memberGenerator.savedMemberWithTeam("박재현", 29, savedTeam);

        // Then
        List<Member> teamMemberList = teamMemberJoinRepositoryQueryMethod.findMemberListByTeamNo(savedTeam.getNo());

        assertEquals(teamMemberList.size(), 3);
        assertEquals(teamMemberList.contains(savedFirstMember), true);
        assertEquals(teamMemberList.contains(savedSecondMember), true);
        assertEquals(teamMemberList.contains(savedThirdMember), true);
    }

    @Test
    @DisplayName("특정 팀의 특정 회원 조회")
    public void findMemberByMemberNo(){
        // Given
        Team savedTeam = teamGenerator.savedTeam();
        Member savedMember = memberGenerator.savedMemberWithTeam("최용석", 31, savedTeam);

        // When
        clearUp();
        Member selectedMember = teamMemberJoinRepositoryQueryMethod.findMemberByNoAndTeamNo(savedMember.getNo(), savedTeam.getNo());

        // Then
        assertEquals(selectedMember.getNo(), savedMember.getNo());
        assertEquals(selectedMember.getClass(), savedMember.getClass());
        assertEquals(selectedMember.getTeam().getNo(), savedTeam.getNo());
        assertNotEquals(selectedMember.getTeam().getClass(), savedTeam.getClass());
    }

    @Test
    @DisplayName("특정 회원의 정보와 해당 회원이 소속된 팀정보 조회")
    public void findMemberAndTeamByMemberNo(){
        // Given
        Team savedTeam = teamGenerator.savedTeam();
        Member savedMember = memberGenerator.savedMemberWithTeam("최용석", 31, savedTeam);

        // When
        Member selectedMember = teamMemberJoinRepositoryQueryMethod.findFetchMemberAndTeamByNo(savedMember.getNo());

        // Then
        clearUp();
        assertEquals(selectedMember.getNo(), savedMember.getNo());
        assertEquals(selectedMember.getTeam().getNo(), savedTeam.getNo());
        assertEquals(selectedMember.getTeam().getClass(), savedTeam.getClass());
    }
}