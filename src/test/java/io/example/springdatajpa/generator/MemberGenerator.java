package io.example.springdatajpa.generator;

import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.domain.entity.Team;
import io.example.springdatajpa.repository.ch02_member_crud.MemberCrudRepositorySpringDataJpa;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : choi-ys
 * @date : 2021/04/05 11:58 오전
 * @Content : 회원 Test case 수행에 필요한 Member Entity 생성
 */
public class MemberGenerator {

    @Autowired
    MemberCrudRepositorySpringDataJpa memberCrudRepositorySpringDataJpa;

    public Member savedMember(){
        return memberCrudRepositorySpringDataJpa.save(createMember());
    }

    public Member savedMemberByParam(String memberName, int age){
        return memberCrudRepositorySpringDataJpa.save(createMemberByParam(memberName, age));
    }

    public Member savedMemberWithTeam(String memberName, int age, Team team){
        return memberCrudRepositorySpringDataJpa.save(
                createMemberWithTeam(memberName, age, team)
        );
    }

    public static Member createMember(){
        String memberName = "최용석";
        int age = 31;
        return createMemberByParam(memberName, age);
    }

    public static Member createMemberByParam(String memberName, int age){
        return memberBuilder(memberName, age, null);
    }

    public static Member createMemberWithTeam(String memberName, int age, Team team){
        return memberBuilder(memberName, age, team);
    }

    private static Member memberBuilder(String memberName, int age, Team team){
        return Member.builder()
                .name(memberName)
                .age(age)
                .team(team)
                .build();
    }
}