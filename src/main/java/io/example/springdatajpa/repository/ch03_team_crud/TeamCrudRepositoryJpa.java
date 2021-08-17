package io.example.springdatajpa.repository.ch03_team_crud;

import io.example.springdatajpa.domain.entity.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author : choi-ys
 * @date : 2021/04/05 7:33 오후
 * @Content : JPA를 이용한 Team Entity와 DB연동 처리 Repository
 */
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TeamCrudRepositoryJpa {

    private final EntityManager entityManager;

    @Transactional
    public Team save(Team team){
        this.entityManager.persist(team);
        return team;
    }

    @Transactional
    public void delete(Team team){
        this.entityManager.remove(team);
    }

    public Optional<Team> findById(long teamNo){
        Team team = this.entityManager.find(Team.class, teamNo);
        return Optional.ofNullable(team);
    }

    public long count() {
        return entityManager.createQuery("select count(t) from Team as t", Long.class)
                .getSingleResult();
    }

    public List<Team> findAll(){
        return entityManager.createQuery("select t from Team as t", Team.class)
                .getResultList();
    }
}