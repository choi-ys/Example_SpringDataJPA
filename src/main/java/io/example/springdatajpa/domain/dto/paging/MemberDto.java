package io.example.springdatajpa.domain.dto.paging;

import io.example.springdatajpa.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author : choi-ys
 * @date : 2021/04/08 3:19 오후
 * @Content : 회원/팀 Entity의 반환을 위한 DTO
 */
@Getter @NoArgsConstructor(access = PROTECTED)
public class MemberDto {

    private long memberNo;
    private String memberName;
    private int age;
    private String teamName;

    public MemberDto(Member member) {
        this.memberNo = member.getNo();
        this.memberName = member.getName();
        this.age = member.getAge();
        if(member.getTeam() != null){
            this.teamName = member.getTeam().getName();
        }
    }
}