package io.example.springdatajpa.repository.ch07_auditing_for_entity_metadata;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : choi-ys
 * @date : 2021/04/08 5:52 오후
 * @Content : 각 Entity의 생성/변경 이력을 관리하기 위해 순수 JPA와 Spring Data Jpa의 제공 기능 확인을 위한 Repository
 */
public interface AuditingRepository extends JpaRepository<Member, Long> {
}