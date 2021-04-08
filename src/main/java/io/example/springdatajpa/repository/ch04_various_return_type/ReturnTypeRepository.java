package io.example.springdatajpa.repository.ch04_various_return_type;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author : choi-ys
 * @date : 2021/04/08 10:57 오전
 * @Content : Spring Data Jpa의 다양한 Return Type 예제
 */
public interface ReturnTypeRepository extends JpaRepository<Member, Long> {

    Member findMemberByNo(long memberNo);

    Optional<Member> findOptionalMemberByNo(long memberNo);

    List<Member> findMemberListByAge(int age);

    Page<Member> findMemberPageListByAge(int age, Pageable pageable);

    Slice<Member> findMemberSliceListByAge(int age, Pageable pageable);
}