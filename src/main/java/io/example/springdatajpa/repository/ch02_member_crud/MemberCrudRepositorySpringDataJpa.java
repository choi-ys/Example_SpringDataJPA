package io.example.springdatajpa.repository.ch02_member_crud;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : choi-ys
 * @date : 2021/04/05 10:55 오전
 * @Content : Spring Data JPA를 이용한 Member Entity와 DB연동 처리 Repository
 */
public interface MemberCrudRepositorySpringDataJpa extends JpaRepository<Member, Long> {
    /**
     * @param startAge 특정 나이 구간 범위 시작 값
     * @param endAge 특정 나이 구간 범위 종료 값
     * @param createdAt 가입일자
     * @param orders 정렬
     * @return
     */
    List<Member> findByAgeBetweenAndCreatedDateGreaterThanEqual(int startAge, int endAge, String lastName, LocalDateTime createdAt, Sort orders);

    @Query("SELECT m " +
            "FROM Member AS m " +
            "WHERE m.age BETWEEN :startAge AND :endAge " +
            "AND m.createdDate >= :createdDate " +
            "ORDER BY :orders")
    List<Member> findBySpecificMember(int startAge, int endAge, LocalDateTime createdDate, Sort orders);
}