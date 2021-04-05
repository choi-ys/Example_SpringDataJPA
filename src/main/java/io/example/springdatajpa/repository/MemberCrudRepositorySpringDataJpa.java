package io.example.springdatajpa.repository;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : choi-ys
 * @date : 2021/04/05 10:55 오전
 * @Content : Spring Data JPA를 이용한 Member Entity와 DB연동 처리 Repository
 */
public interface MemberCrudRepositorySpringDataJpa extends JpaRepository<Member, Long> {
}