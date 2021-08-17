package io.example.springdatajpa.repository.ch04_various_query_method_expression;

import io.example.springdatajpa.domain.entity.Member;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : choi-ys
 * @date : 2021/08/15 3:20 오후
 * @apiNote :
 */
public interface MemberRepositoryQueryMethodExpression extends JpaRepository<Member, Long> {

    /**
     * findBy...GreaterThanEqual : 이상 (기준 파라미터 포함, 실행 쿼리: 조회 조건 컬럼 >= 파라미터)
     * findBy...After, findBy...GreaterThan : 초과 (기준 파라미터 미 포함, 실행 쿼리: 조회 조건 컬럼 > 파라미터)
     *
     * findBy...LessThanEqual : 이하 (기준 파라미터 포함, 실행쿼리: 조회 조건 컬럼 <= 파라미터)
     * findBy...Before, findBy...LessThan : 미만 (기준 파라미터 미 포함, 실행쿼리: 조회 조건 컬럼 < 파라미터)
     *
     * findBy...Between : 범위 (양끝의 조건을 포함, 실행쿼리: 조회 조건 between 파라미터 and 파라미터)
     * @param criteriaAge
     */
    List<Member> findByAgeAfter(int criteriaAge);
    List<Member> findByAgeGreaterThan(int criteriaAge);
    List<Member> findByAgeGreaterThanEqual(int criteriaAge);

    List<Member> findByAgeBefore(int criteriaAge);
    List<Member> findByAgeLessThan(int criteriaAge);
    List<Member> findByAgeLessThanEqual(int criteriaAge);
    
    List<Member> findByAgeBetween(int startAge, int endAge);

    List<Member> findByCreatedDateAfter(LocalDateTime criteriaDateTime);
    List<Member> findByCreatedDateGreaterThan(LocalDateTime criteriaDateTime);
    List<Member> findByCreatedDateGreaterThanEqual(LocalDateTime criteriaDateTime);

    List<Member> findByCreatedDateBefore(LocalDateTime criteriaDateTime);
    List<Member> findByCreatedDateLessThan(LocalDateTime criteriaDateTime);
    List<Member> findByCreatedDateLessThanEqual(LocalDateTime criteriaDateTime);

    List<Member> findByCreatedDateBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    /**
     * findBy...IsNull : 널 여부 판별(실행쿼리 : 조회 조건 컬럼 is null)
     * findBy...IsNotNull : 널 여부 판별(실행쿼리 : 조회 조건 컬럼 is not null)
     */
    List<Member> findByAgeIsNull();
    List<Member> findByAgeIsNotNull();

    /**
     * findBy...In : 포함 여부 판별 (실행쿼리 in (파라미터))
     * findBy...NotIn : 미 포함 여부 판별 (실행쿼리 : not in (파라미터))
     */
    List<Member> findByNameIn(List<String> nameList);
    List<Member> findByNameNotIn(List<String> nameList);

    /**
     * findBy...StartingWith -> 해당 파라미터로 시작 조건 (실행쿼리 : like str%)
     * findBy...EndingWith -> 해당 파라미터로 종료 조건 (실행쿼리 : like %str)
     * findBy...Contains -> 해당 파라미터 포함 조건 (실행쿼리 : like %str%, JPA Like문과 동일)
     * findBy...Like -> 해당 파라미터 포함 조건 (실행쿼리 : like %str%, JPA Contains문과 동일)
     */
    List<Member> findByNameStartingWith(String name);
    List<Member> findByNameEndingWith(String name);
    List<Member> findByNameContains(String name); // 권장 : JPA에서 해당 구문 실행 시 파라미터 앞뒤로 "%"를 추가
    List<Member> findByNameLike(String name); // 해당 절에 파라미터 앞뒤로 "%"를 코드에 명시해야 하므로 Contains를 권장

    /**
     * 정렬과 limit 조건을 활용한 순위 산출
     * findBy...First -> 조건을 만족하는 첫번째 요소 조회
     * findBy...Top -> 조건을 만족하는 첫번째 요소 조회
     * findBy...TopOrderBy...Desc -> 조건을 만족하는 마지막 요소 조회
     * First와 Top키워드 뒤에 숫자 조건을 명시하여, 조건에 부합하는 반환된 행의 갯수 지정 가능
     *  - ex) First100, Top100 -> limit 100
     * FirstN, TopN키워드와 OrderBy...Asc, OrderBy...Desc조건을 활용하여 순위 산출
     */
    Member findFirstBy(Sort orders); // 권장 : Order By에 해당 하는 인자를 Sort 객체로 받아서 처리
    Member findFirstByOrderByAgeAsc();
    Member findTopByOrderByAgeAsc();
    List<Member> findTop2ByOrderByAgeDesc();
}
