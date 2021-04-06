package io.example.springdatajpa.repository.ch03_team_member_join;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author : choi-ys
 * @date : 2021/04/06 1:58 오후
 * @Content : Spring Data JPA의 Query Annotation를 이용한 회원과 팀의 연관관게 Join 연산
 *  - 특정 팀에 소속된 회원 목록 조회
 *  - 특정 팀의 특정 회원 조회
 *  - 특정 회원의 정보와 해당 회원이 소속된 팀정보
 */
public interface TeamMemberJoinRepositoryQueryAnnotation extends JpaRepository<Member, Long> {

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
     * @param teamNo
     * @return
     */
    @Query("select m from Member as m join m.team as t where t.no = :teamNo")
    List<Member> findMemberListByTeamNo(@Param("teamNo") long teamNo);

    /** 특정 팀의 특정 회원 조회
     *     select
     *         member0_.member_no as member_n1_0_,
     *         member0_.age as age2_0_o
     *         member0_.member_name as member_n3_0_,
     *         member0_.team_no as team_no4_0_
     *         member_tb member0_
     *     inner join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         member0_.member_no=1
     * @param memberNo
     * @return
     */
    @Query("select m from Member as m join m.team as t where m.no = :memberNo")
    Member findMemberByNo(@Param("memberNo") long memberNo);

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
     */
    @Query("select m from Member as m join fetch m.team as t where m.no = :memberNo")
    Member findMemberByMemberNo(@Param("memberNo") long memberNo);
}