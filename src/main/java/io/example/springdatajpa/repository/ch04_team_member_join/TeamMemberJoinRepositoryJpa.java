package io.example.springdatajpa.repository.ch04_team_member_join;

import io.example.springdatajpa.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * @author : choi-ys
 * @date : 2021/04/06 9:51 오전
 * @Content : 순수 JPA를 이용한 회원과 팀의 연관관계 Join 연산
 *  - 특정 팀에 소속된 회원 목록 조회
 *  - 특정 팀의 특정 회원 조회
 *  - 특정 회원의 정보와 해당 회원이 소속된 팀정보
 */
@Repository
@RequiredArgsConstructor
public class TeamMemberJoinRepositoryJpa {

    private final EntityManager entityManager;

    /** 특정 팀에 소속된 회원 목록 조회
     *     select
     *         member0_.member_no as member_n1_0_,
     *         member0_.age as age2_0_,
     *         member0_.member_name as member_n3_0_,
     *         member0_.team_no as team_no4_0_
     *     from
     *         member_tb member0_
     *     inner join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         team1_.team_no=1
     * @param teamNo 팀 번호
     * @return List<Member> 회원 목록
     */
    public List<Member> findMemberListByTeamNo(long teamNo){
        return entityManager.createQuery("select m from Member as m" +
                " join m.team as t" +
                " where t.no = :teamNo", Member.class)
                .setParameter("teamNo", teamNo)
                .getResultList();
    }

    /** 특정 팀의 특정 회원 조회
     *     select
     *         member0_.member_no as member_n1_0_,
     *         member0_.age as age2_0_,
     *         member0_.member_name as member_n3_0_,
     *         member0_.team_no as team_no4_0_
     *     from
     *         member_tb member0_
     *     inner join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         member0_.member_no=1
     * @param memberNo 회원 번호
     * @return Member 회원정보
     */
    public Member findMemberByMemberNo(long memberNo){
        return entityManager.createQuery("select m from Member as m" +
                " join m.team as t" +
                " where m.no = :memberNo", Member.class)
                .setParameter("memberNo", memberNo)
                .getSingleResult();
    }

    /** 특정 회원의 정보와 해당 회원이 소속된 팀정보
     *     select
     *         member0_.member_no as member_n1_0_0_,
     *         team1_.team_no as team_no1_1_1_,
     *         member0_.age as age2_0_0_,
     *         member0_.member_name as member_n3_0_0_,
     *         member0_.team_no as team_no4_0_0_,
     *         team1_.team_name as team_nam2_1_1_
     *     from
     *         member_tb member0_
     *     inner join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         member0_.member_no=1
     * @param memberNo
     * @return
     */
    public Member findMemberAndTeamByMemberNo(long memberNo){
        return entityManager.createQuery("select m from Member as m" +
                " join fetch m.team as t" +
                " where m.no = :memberNo", Member.class)
                .setParameter("memberNo", memberNo)
                .getSingleResult();
    }
}