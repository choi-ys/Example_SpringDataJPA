package io.example.springdatajpa.repository.ch01_member_crud;

import io.example.springdatajpa.domain.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

/**
 * @author : choi-ys
 * @date : 2021/04/05 10:55 오전
 * @Content : JPA를 이용한 Member Entity와 DB연동 처리 Repository
 */
@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberCrudRepositoryJpa {

    private final EntityManager entityManager;

    @Transactional
    public Member save(Member member){
        this.entityManager.persist(member);
        return member;
    }

    @Transactional
    public void delete(Member member){
        this.entityManager.remove(member);
    }

    public Optional<Member> findById(long memberNo){
        Member member = this.entityManager.find(Member.class, memberNo);
        return Optional.ofNullable(member);
    }

    public long count() {
        return entityManager.createQuery("select count(m) from Member as m", Long.class)
                .getSingleResult();
    }

    /**
     * JPQL(객체지향쿼리)을 이용한 Member Entity 목록 조회
     *  - JPQL을 이용하여 개발자는 객체를 중심으로 Query,
     *   실행 시점에 JPA는 JPQL에 명시된 객체를 기반으로 SQL을 생성 및 수행하여 결과를 반환
     *  - 주의 : JPQL 실행 시점에 flush가 발생하여 쓰기 지연 저장소에 저장된 SQL이 실행 된다.
     * @return
     */
    public List<Member> findAll(){
        return entityManager.createQuery("select m from Member as m", Member.class)
                .getResultList();
    }
}