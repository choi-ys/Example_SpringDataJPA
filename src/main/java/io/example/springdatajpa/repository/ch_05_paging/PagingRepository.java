package io.example.springdatajpa.repository.ch_05_paging;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author : choi-ys
 * @date : 2021/04/08 2:12 오후
 * @Content : Spring Data Jpa를 이용한 페이징 처리
 *  - 페이징과 정렬 파라미터
 *    - org.springframework.data.domain.Sort : 정렬 기능
 *    - org.springframework.data.domain.Pageable : 페이징 기능 (내부에 Sort 포함)
 *  - 특별한 반환 타입
 *    - org.springframework.data.domain.Page : 추가 count 쿼리 결과를 포함하는 페이징
 *    - org.springframework.data.domain.Slice : 추가 count 쿼리 없이 다음 페이지만 확인 가능 (내부적으로 limit + 1조회)
 */
public interface PagingRepository extends JpaRepository<Member, Long> {

    /**
     * Page타입을 반환하는 쿼리가 단일 Table 조회가 아닌 여러 Table의 Join인 형태인 경우
     * countQuery 옵션을 이용하여 paging에 필요한 totalCount를 최적화 할 수 있다.
     * @param age
     * @param pageable
     * @return
     */
    @Query(value = "select m from Member as m" +
            " left join fetch m.team as t" +
            " where m.age = :age",
            countQuery = "select count(m) from Member as m" +
                    " where m.age = :age"
    )
    Page<Member> findMemberWithTeamPageListByAge(@Param("age") int age, Pageable pageable);

    @Query(value = "select m from Member as m" +
            " left join m.team as t" +
            " where m.age = :age")
    Slice<Member> findMemberWithTeamSliceListByAge(@Param("age") int age, Pageable pageable);
}