Bulk Update/Delete (대용량 데이터 수정/삭제)
 - EX) JPA에서 대용량의 Record를 업데이트 하는 경우(예를 든 상황이 Batch성격을 띄긴하지만 어드민에서 한번에 많은 양의 데이터를 수정하는 경우라고 가정)
   모든 Record를 조회하지 않고 쿼리로 처리 할 수 있는 deleteAllInBatch와 같은 메소드를 별도로 제공하지 않기 때문에 
   수정 대상이 되는 Record를 수정하기 위해서는 다음과 같은 Flow가 동반되는데,
    - 1. 수정 대상이 되는 Record를 Dirty Checking으로 변경하기 위해 각각의 Record를 조회하여 Persist Context에 저장한다. (이때 조회 된 각각의 Record는 Persist 상태) 
    - 2. Persist상태로 조회된 각각의 Entity에 값을 변경하여 Transaction 종료 시점에 Flush를 통해 Dirty Checking으로 하여금 Record를 수정한다.
   이는 건별로 조회 -> 수정의 트랜잭션이 발생하므로 데이터의 양이 많아질수록 성능상 부하가 발생할 수 있다.
 
   해결 방법 1 : 이러한 부하를 줄이고 성능상 이점을 가져가기 위해 직접 SQL을 명시하는 Native Query를 고려할 수 있다.
   해결 방법 2 : Spring Data JPA는 이를 해결하기 위해 다음과 같은 방법을 제시한다.
   ```
   @Modifying(clearAutomatically = true)
   @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
   int bulkAgePlus(@Param("age") int age)
   참고: 벌크 연산은 영속성 컨텍스트를 무시하고 실행하기 때문에, 영속성 컨텍스트에 있는 엔티티의 상태와
   DB에 엔티티 상태가 달라질 수 있다.
   > 권장하는 방안
   > 1. 영속성 컨텍스트에 엔티티가 없는 상태에서 벌크 연산을 먼저 실행한다.
   > 2. 부득이하게 영속성 컨텍스트에 엔티티가 있으면 벌크 연산 직후 영속성 컨텍스트를 초기화 한다
   ```
     