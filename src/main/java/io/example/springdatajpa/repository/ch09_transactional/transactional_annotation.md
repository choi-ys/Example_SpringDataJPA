@Transactional (Spring Boot Annotation)
===
> Checked exception
 - 반드시 처리해야 하는 예외
 - @Tansactional의 Rollback 대상에 미포함
   - @Transactional(rollbackFor = Exception.class)를 명시할 경우 rollback 대상에 포함
 - EX) IOException, SQLException 

> Uncheck exception
 - 처리하지 않아도 되는 예외
 - @Tansactional의 Rollback 대상에 포함
 - EX) RuntimeException, NullPointException, IllegalArgumentException
 - 권장 : Checked Exception은 기본 트랜잭션 속성에서는 rollback 진행 대상이 아니므로, 구체적인 UncheckedException(RunTimeException을 상속받은)을 발생시켜 예외에 대한 메세지를 명확하게 전달하고 로직의 흐름을 끊어 처리
 
 참조 URL : [Checked Exception을 대하는 자세](https://cheese10yun.github.io/checked-exception/)
---

## @Transactional 미적용 사례
```
Spring container에 등록된 bean의 특정 method가 호출 되는 경우, 호출 시점에 aop에 의해 해당 method에 명시된 Annotaion을 인식한다.
따라서 TransactionalNotWorkingService의 wrapMethod를 호출하는 경우, wrapMethod가 내부적으로 호출하는 actualMethod에 명시된 @Transactinal은
TransactionalNotWorkingService bean 진입 시점에 인식되지 않으므로 동작하지 않는다.

```
```java
import io.example.springdatajpa.domain.entity.Member;import org.springframework.data.jpa.repository.JpaRepository;import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionalNotWorkingService(){
    private final MemberRepository memberRepository;
    
    public void wrapMethod(){
        actualMethod();
    }
    
    @Transactional
    public void actualMethod(){
        // Given
        String name = "최용석";
        int age = 31;
        Member member = new Member(name, age);

        // When
        memberRepository.save(member);
        
        // Then
        throw new RuntimeException();
    }


}

public interface MemberRepository extends JpaRepository<Member, Long>{
}

@SpringBootTest
@RequiredArgsConstructor
public class TransactionalNotWorkingServiceTest{
    private final TransactionalNotWorkingService transactionalNotWorkingService;

    @Test
    @DisplayName("@Transactional 미 동작")
    public void transactionalNotWorking(){
        transactionalNotWorkingService.wrapMethod();
    }

    @Test
    @DisplayName("@Transactional 동작")
    public void transactionalNotWorking(){
        transactionalNotWorkingService.actualMethod();
    }


}
```
---

## @Transactional의 속성
> isolation : 트랜잭션 격리 단계, 동시에 발생하는 트랜잭션간 데이터 접근 수준
> > 트랜잭션의 네 가지 주요 성질인 ACID(원자성, 일관성, 고립성, 내구성)을 구현하는 개념
> > > 고립성 : 한 트랜잭션에서 데이터가 수정되는 과정이 다른 트랜잭션과는 독립적으로 진행

[isolation](https://miro.medium.com/max/475/1*hEpucQJzGE6K7D9M_0bEVw.gif)
단계별 격리 단계
```
단계가 높을 수록 격리수준이 강력하고, 데이터의 무결성과 고립성을 보장하나, 동시성과 성능의 저하가 발생
``` 
   
   - DEFAULT : DB의 기본 격리단계 (MySQL의 default는 REPEATABLE_READ)
   - (Level 0) READ_UNCOMMITTED : Commit되지 않은 데이터 조회 가능
      - 문제점 : `Dirty read` : 다른 트랜잭션에서 Commit되지 않은 데이터를 조회 가능한 현상 
      - 트랜잭션이 병합되는 경우(서로 다른 트랜잭션이 동시에 한 데이터에 접근하는 경우) 데이터의 정합성을 보장하지 않으므로 사용 미 권장
   - (Level 1) READ_COMMITTED : Commit된 데이터만 조회 가능
     - 문제점 : 동일 트랜잭션 내에서 반복적으로 데이터를 조회했을때, 다른 트랜잭션에서 Commit되어 데이터가 변경될 수 있는 현상을 'UnRepeatable' 이라고 표현
   - (Level 2) REPEATABLE_READ : 동일 트랜잭션 내에서 반복해서 값을 조회하여도, 항상 동일한 결과값을 보장
     - 트랜잭션 시작 시 조회 대상의 데이터를 별도의 스냅샷으로 저장하여, 반복해서 값을 조회하여도 트랜잭션 종료 시 까지 스냅샷의 데이터를 반환하여
       항상 동일한 값을 보장  
     - 문제점 : 팬텀리드
   - (Level 3) SERIALIZABLE : 
     - 

참조 URL [격리 수준 ](https://miro.medium.com/max/475/1*hEpucQJzGE6K7D9M_0bEVw.gif)

> propagation
 - REQUIRED :
 - REQUIRED_NEW : 
 - NESTED : 
 - SUPPORTS :
 - NOT_SUPPORTED :  
 - MANDATORY : 
 - NEVER : 
 

 ---
 
 ## 
 참조URL [Dirty chekcing시 변경된 항목만 비교해서 update 쿼리를 구성하는 JPA의 @DynamicUpdate](https://velog.io/@freddiey/JPA%EC%9D%98-DynamicUpdate)