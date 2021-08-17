package io.example.springdatajpa.repository.ch03_team_member_crud;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.domain.entity.Team;
import io.example.springdatajpa.generator.MemberGenerator;
import io.example.springdatajpa.generator.TeamGenerator;
import io.example.springdatajpa.repository.ch02_member_crud.MemberCrudRepositorySpringDataJpa;
import io.example.springdatajpa.repository.ch03_team_crud.TeamCrudRepositorySpringDataJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author : choi-ys
 * @date : 2021/04/06 6:28 오후
 * @Content : Spring Data JPA를 이용한 연관관계를 가지는 Entity Insert Test
 */
@DisplayName("CrudRepository[SpringDataJpa]:TeamMember")
public class TeamMemberCrudRepositorySpringDataJpaTest extends BaseTest {

    @Resource
    TeamCrudRepositorySpringDataJpa teamCrudRepositorySpringDataJpa;

    @Resource
    MemberCrudRepositorySpringDataJpa memberCrudRepositorySpringDataJpa;

    @Test
    @DisplayName("팀에 소속된 회원 정보 저장")
    public void saveMember(){
        // Given
        Team team = TeamGenerator.createTeam();
        Team savedTeam = teamCrudRepositorySpringDataJpa.save(team);
        assertEquals(savedTeam, team);
        assertEquals(savedTeam.getName(), team.getName());

        String memberName = "최용석";
        int age = 31;
        Member member = MemberGenerator.createMemberWithTeam(memberName, age, team);
        Member savedMember = memberCrudRepositorySpringDataJpa.save(member);
        assertEquals(savedMember, member);
        assertEquals(savedMember.getNo(), member.getNo());
        assertEquals(savedMember.getName(), member.getName());
        assertEquals(savedMember.getAge(), member.getAge());

        // Then : 양방향 연관관계의 값 설정 여부 화인
        assertEquals(team.getMemberList().contains(savedMember), true);
        assertEquals(member.getTeam(), savedTeam);
    }
}