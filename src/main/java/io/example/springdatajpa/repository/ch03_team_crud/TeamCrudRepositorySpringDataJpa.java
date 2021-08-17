package io.example.springdatajpa.repository.ch03_team_crud;

import io.example.springdatajpa.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : choi-ys
 * @date : 2021/04/05 7:36 오후
 * @Content : Spring Data JPA를 이용한 Team Entity와 DB연동 처리 Repository
 */
public interface TeamCrudRepositorySpringDataJpa extends JpaRepository<Team, Long> {
}