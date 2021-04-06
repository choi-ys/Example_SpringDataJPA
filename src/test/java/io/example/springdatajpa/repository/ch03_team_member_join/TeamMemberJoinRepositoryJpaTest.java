package io.example.springdatajpa.repository.ch03_team_member_join;

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
 * @date : 2021/04/06 9:11 오전
 * @Content : 순수 JPA를 이용한 Team과 Member 연관관계의 Join 쿼리 Test
 *  - 특정 팀에 소속된 회원 목록 조회
 *  - 특정 팀의 특정 회원 조회
 *  - 특정 회원의 정보와 해당 회원이 소속된 팀정보
 */
@DisplayName("JoinRepository[JPA]:TeamMember")
@Import({TeamGenerator.class, MemberGenerator.class})
public class TeamMemberJoinRepositoryJpaTest extends BaseTest {

    @Resource
    TeamGenerator teamGenerator;

    @Resource
    MemberGenerator memberGenerator;

    @Resource
    TeamMemberJoinRepositoryJpa teamMemberJoinRepositoryJpa;

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

        // When
        List<Member> selectedMemberList = teamMemberJoinRepositoryJpa.findMemberListByTeamNo(savedTeam.getNo());

        // Then
        assertEquals(selectedMemberList.size(), 3);
        assertEquals(selectedMemberList.contains(savedFirstMember), true);
        assertEquals(selectedMemberList.contains(savedSecondMember), true);
        assertEquals(selectedMemberList.contains(savedThirdMember), true);
    }

    @Test
    @DisplayName("특정 팀의 특정 회원 조회")
    public void findMemberByMemberNo(){
        // Given
        Team savedTeam = teamGenerator.savedTeam();
        Member savedMember = memberGenerator.savedMemberWithTeam("최용석", 31, savedTeam);

        // When
        clearUp();
        Member selectedMember = teamMemberJoinRepositoryJpa.findMemberByMemberNo(savedMember.getNo());

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
        clearUp();
        Member selectedMember = teamMemberJoinRepositoryJpa.findMemberAndTeamByMemberNo(savedMember.getNo());

        // Then
        assertEquals(selectedMember.getNo(), savedMember.getNo());
        assertEquals(selectedMember.getTeam().getNo(), savedTeam.getNo());
        assertEquals(selectedMember.getTeam().getClass(), savedTeam.getClass());
    }
}