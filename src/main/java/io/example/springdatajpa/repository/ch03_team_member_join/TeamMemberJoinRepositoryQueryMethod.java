package io.example.springdatajpa.repository.ch03_team_member_join;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author : choi-ys
 * @date : 2021/04/06 11:44 오전
 * @Content : Spring Data JPA의 Query Method를 이용한 회원과 팀의 연관관계 join 연산
 *  - 특정 팀에 소속된 회원 목록 조회
 *  - 특정 팀의 특정 회원 조회
 *  - 특정 회원의 정보와 해당 회원이 소속된 팀정보
 */
public interface TeamMemberJoinRepositoryQueryMethod extends JpaRepository<Member, Long> {

    /** 특정 팀에 소속된 회원 목록 조회
     *     select
     *         member0_.member_no as member_n1_0_,
     *         member0_.age as age2_0_,
     *         member0_.member_name as member_n3_0_,
     *         member0_.team_no as team_no4_0_
     *     from
     *         member_tb member0_
     *     left outer join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         team1_.team_no=1
     * @param teamNo
     * @return
     */
    List<Member> findMemberListByTeamNo(long teamNo);

    /** 특정 팀의 특정 회원 조회
     *     select
     *         member0_.member_no as member_n1_0_,
     *         member0_.age as age2_0_,
     *         member0_.member_name as member_n3_0_,
     *         member0_.team_no as team_no4_0_
     *     from
     *         member_tb member0_
     *     left outer join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         member0_.member_no=1
     *         and team1_.team_no=1
     * @param memberNo
     * @param teamNo
     * @return
     */
    Member findMemberByNoAndTeamNo(long memberNo, long teamNo);

    /** 특정 회원의 정보와 해당 회원이 소속된 팀 정보
     *     select
     *         member0_.member_no as member_n1_0_0_,
     *         team1_.team_no as team_no1_1_1_,
     *         member0_.age as age2_0_0_,
     *         member0_.member_name as member_n3_0_0_,
     *         member0_.team_no as team_no4_0_0_,
     *         team1_.team_name as team_nam2_1_1_
     *     from
     *         member_tb member0_
     *     left outer join
     *         team_tb team1_
     *             on member0_.team_no=team1_.team_no
     *     where
     *         member0_.member_no=1
     * @param memberNo
     * @return
     */
    @EntityGraph(attributePaths = "team")
    Member findFetchMemberAndTeamByNo(long memberNo);

}
