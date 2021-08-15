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

    // 단건 조회 : 데이터가 없는 경우 Null
    Member findMemberByNo(long memberNo);

    // 단건의 Optional 조회
    Optional<Member> findOptionalMemberByNo(long memberNo);

    // Collection 조회 : 데이터가 없는 경우 비어있는 Collection을 반환 -> size = 0 (주의 : Null이 아님)
    List<Member> findMemberListByAge(int age);

    // Collection 조회시 page 처리ReturnTypeRepository
    Page<Member> findMemberPageListByAge(int age, Pageable pageable);

    // Collection 조회 시 slice 처리
    Slice<Member> findMemberSliceListByAge(int age, Pageable pageable);
}