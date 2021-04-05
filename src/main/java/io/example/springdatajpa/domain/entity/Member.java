package io.example.springdatajpa.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author : choi-ys
 * @date : 2021/04/05 10:32 오전
 * @Content : 회원을 표현하는 Entity
 */
@Entity @Table(name = "member_tb")
@SequenceGenerator(
        name = "MEMBER_ENTITY_SEQ_GENERATOR",
        sequenceName = "MEMBER_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Member {

    @Id @GeneratedValue(generator = "MEMBER_ENTITY_SEQ_GENERATOR")
    @Column(name = "member_no")
    private Long no;

    @Column(name = "member_name", nullable = false, length = 30)
    private String name;

    @Column(name ="age", nullable = true)
    private int age;

    // * --------------------------------------------------------------
    // * Header : 도메인 생성
    // * @author : choi-ys
    // * @date : 2021/04/05 2:19 오후
    // * --------------------------------------------------------------

    @Builder
    public Member(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // * --------------------------------------------------------------
    // * Header : 비즈니스 로직
    // * @author : choi-ys
    // * @date : 2021/04/05 2:19 오후
    // * --------------------------------------------------------------

    /**
     * 회원 이름 변경
     * @param changedMemberName 변경할 회원 이름
     */
    public void changeMemberName(String changedMemberName){
        this.name = changedMemberName;
    };
}
