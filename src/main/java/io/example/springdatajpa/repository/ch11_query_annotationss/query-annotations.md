@Query : Spring Data JPA Annotations
===

## Query Method vs Query Annotations
```
이미 DB와 객체가 매핑 관계를 가진 Spring Data Jpa를 사용하는 환경에서의 @Query Annotation은 그다지 활용성이 높지 않다.
하지만 무작정 배제하기보다는 다음과 같은 상황을 이해하고 Query Method와 @Query Annotations를 다시 바라보자

많은 조건을 표현해야 하는 경우 Query Method의 가독성 문제
 - EX) 다음과 같은 조건을 만족하는 회원 목록 조회
   - 특정 나이 구간대를 만족하는 회원
   - 특정 기간 이후 가입한 회원
```
Query Method
```
    /**
     * @param startAge 특정 나이 구간 범위 시작 값
     * @param endAge 특정 나이 구간 범위 종료 값
     * @param createdAt 가입일자
     * @param orders 정렬
     * @return
     */
    List<Member> findByAgeBetweenAndCreatedDateGreaterThanEqual(int startAge, int endAge, String lastName, LocalDateTime createdAt, Sort orders);
```
@Query Annotations
```
    @Query("SELECT m " +
            "FROM Member AS m " +
            "WHERE m.age BETWEEN :startAge AND :endAge " +
            "AND m.createdDate >= :createdAt " +
            "ORDER BY :orders")
    List<Member> findBySpecificMember(@Param int startAge,
                                        @Param int endAge, 
                                        @Param LocalDateTime createdAt, 
                                        @Param Sort orders);
```
```
Query Method의 경우 운영 하며 수정사항이 발생하는 경우 가독성이 점점 떨어지게 되며, 추가로 수정 사항이 발생하는 경우
Method명으로 실행 Query를 추측할 수 있는 원래 목적과는 달리 점점 가독성이 떨어지게 된다.
반면, @Query Annotations를 사용 하는 경우 우리가 익숙하게 봐왔던 SQL과 흡사한 JPQL로 구성하게 되어, 
복잡한 조회 조건을 만족해야하는 쿼리인 경우에도 충분히 가독성에서 이점이 있다.
하지만, String으로 JPQL을 명시함으로써 Type safe 하지 않다는 단점이 있다.
이는 QueryDSL을 활용하여 복잡한 쿼리를 가독성과 Type safe를 모두 만족하며 구성함으로써 해결 할 수 있다.  
```
