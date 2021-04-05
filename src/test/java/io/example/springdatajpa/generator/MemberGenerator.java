package io.example.springdatajpa.generator;

import io.example.springdatajpa.domain.entity.Member;

/**
 * @author : choi-ys
 * @date : 2021/04/05 11:58 오전
 * @Content : 회원 Test case 수행에 필요한 Member Entity 생성
 */
public class MemberGenerator {

    public static Member createMember(){
        String memberName = "최용석";
        int age = 31;
        return memberBuilder(memberName, age);
    }

    public static Member createMemberByMemberName(String memberName, int age){
        return memberBuilder(memberName, age);
    }

    private static Member memberBuilder(String memberName, int age){
        return Member.builder()
                .name(memberName)
                .age(age)
                .build();
    }
}