package io.example.springdatajpa.domain.entity;

import io.example.springdatajpa.repository.ch07_auditing_for_entity_metadata.domain.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
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
//public class Member extends JpaBaseEntity {
public class Member extends BaseTimeEntity {
//public class Member extends BaseEntity {

    @Id @GeneratedValue(generator = "MEMBER_ENTITY_SEQ_GENERATOR")
    @Column(name = "member_no")
    private Long no;

    @Column(name = "member_name", nullable = false, length = 30)
    private String name;

    @Column(name ="age", nullable = true)
    private int age;

    // * --------------------------------------------------------------
    // * Header : Entity의 연관관계 설정
    // * @author : choi-ys
    // * @date : 2021/04/05 7:58 오후
    // * --------------------------------------------------------------
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "team_no", foreignKey = @ForeignKey(name = "PK_MEMBER_TEAM"))
    private Team team;

    // * --------------------------------------------------------------
    // * Header : 양방향 연관관계 객체의 값 설정
    // * @author : choi-ys
    // * @date : 2021/04/05 7:57 오후
    // * --------------------------------------------------------------
    public void setTeam(Team team){
        this.team = team;
        team.getMemberList().add(this);
    }

    // * --------------------------------------------------------------
    // * Header : 도메인 생성
    // * @author : choi-ys
    // * @date : 2021/04/05 2:19 오후
    // * --------------------------------------------------------------

    @Builder
    public Member(String name, int age, Team team) {
        this.name = name;
        this.age = age;
        if(team != null){
            this.setTeam(team);
        }
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
