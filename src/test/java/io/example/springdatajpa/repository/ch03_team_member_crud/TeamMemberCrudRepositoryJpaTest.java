package io.example.springdatajpa.repository.ch03_team_member_crud;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.domain.entity.Team;
import io.example.springdatajpa.generator.MemberGenerator;
import io.example.springdatajpa.generator.TeamGenerator;
import io.example.springdatajpa.repository.ch01_member_crud.MemberCrudRepositoryJpa;
import io.example.springdatajpa.repository.ch02_team_crud.TeamCrudRepositoryJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : choi-ys
 * @date : 2021/04/06 6:23 오후
 * @Content : 순수 JPA를 이용한 연관관계를 가지는 Entity Insert Test
 */
@DisplayName("CrudRepository[JPA]:TeamMember")
public class TeamMemberCrudRepositoryJpaTest extends BaseTest {

    @Resource
    TeamCrudRepositoryJpa teamCrudRepositoryJpa;

    @Resource
    MemberCrudRepositoryJpa memberCrudRepositoryJpa;

    @Test
    @DisplayName("팀에 소속된 회원 정보 저장")
    public void saveMember(){
        // Given
        Team team = TeamGenerator.createTeam();
        Team savedTeam = teamCrudRepositoryJpa.save(team);
        assertEquals(savedTeam, team);
        assertEquals(savedTeam.getName(), team.getName());

        String memberName = "최용석";
        int age = 31;
        Member member = MemberGenerator.createMemberWithTeam(memberName, age, team);
        Member savedMember = memberCrudRepositoryJpa.save(member);
        assertEquals(savedMember, member);
        assertEquals(savedMember.getNo(), member.getNo());
        assertEquals(savedMember.getName(), member.getName());
        assertEquals(savedMember.getAge(), member.getAge());

        // Then : 양방향 연관관계의 값 설정 여부 화인
        assertEquals(team.getMemberList().contains(savedMember), true);
        assertEquals(member.getTeam(), savedTeam);
    }
}