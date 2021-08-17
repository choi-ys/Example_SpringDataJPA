Entity
===
```
Entity
 - 데이터베이스 테이블에 대응하는 Java 클래스
 - @Entity 어노테이션이 표기된 객체는 데이터베이스 테이블과 매핑되며, Persist context의 영속 상태 관리 대상이 되는 객체
   - Persist context의 4가지 영속상태
     - New(비영속) : 데이터베이스와 연동된 적 없는 순수한 java객체로 Entity manager의 관리 대상에 포함되지 않음
     - Managed(영속) : 데이터베이스에 저장되고, 메모리상에서도 같은 상태로 존재
     - Removed(삭제) : 데이터베이스상에서 삭제된 상태, Persist context에 존재 하지 않음 
     - Detached(준 영속) : Persist context에서 Entity를 접근해서 사용하는 상태, 아직 데이터베이스와 동기화가 이루어지지 않은 상태
```
---
## JPA의 Entity관련 Annotations (Class Annotations)
> @Entity : JPA에서 관리하는 엔티티 클래스 지정 시 사용 
- name 속성 : 엔티티의 이름을 지정
  - default : 자바 클래스명 (권장:default)

---
> @Table : 테이블과 매핑될 엔티티의 속성 설정 시 사용
 - name 속성 : Entity와 매핑되는 데이터베이스 테이블명 지정, 생략 시 @Entity의 name속성 값의 테이블과 매핑
 - uniqueConstraints 속성 : 엔티티를 이용한 DDL 생성 시, 해당 설정의 unique 제약조건을 생성, 2개 이상의 복합 unique 제약조건도 설정 가능
   - name 속성 : unique 제약 조건 명
   - columnNames 속성 : unique 제약조건이 적용될 필드
 - indexes 속성 : Entity의 필드와 매핑되는 컬럼에 인덱싱 설정
 - catalog 속성 : catalog를 제공하는 DB의 catalog와 매핑
   - catalog : DB 개체들에 대한 정의를 담고 있는 메타데이터, 데이터 사전(Data Dictionary)라고도 표현
   - 테이블, 뷰, synonym, 값 범위, 인덱스 사용자 그룹 등의 정보를 포함
 - schema 속성 : schema를 제공하는 DB의 schema와 매핑
   - schema : 테이블의 형태를 표현한 메타 데이터
   - 데이터베이스에 저장되는 데이터와 구조, 관계, 테이블의 속성 등의 정보를 포함

```java
@Table(
        name = "example_tb",
        uniqueConstraints = @UniqueConstraint(
                name = "EMAIL_UNIQUE",
                columnNames = "email"
        ),
        indexes = {
                @Index(columnList = "createdDate")
        }
)
```

---
> @SequenceGenerator : DDL생성 시 Sequence객체 생성 및 매핑 설정
 - name 속성 : Sequence를 생성하는 SequenceGenerator의 이름을 지정
 - sequenceName 속성 : 생성 또는 매핑할 Sequence 이름을 지정
 - initialValue 속성 : 최초 생성 시 Sequence의 초기값을 지정
 - allocationSize 속성 : 다음 Sequence의 증가값 지정
```java
@SequenceGenerator(
        name = "EXAMPLE_ENTITY_SEQ_GENERATOR",
        sequenceName = "EXAMPLE_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
```
---
## JPA의 Entity관련 Annotations (Feild Annotations)
> @Id : Record를 구분하기 위한 PK를 표현하는 Annotations

> @GeneratedValue : PK값의 자동 생성 전략 설정
 - generator : PK생성 시 사용할 SequenceGenerator의 name을 명시
 - strategy : PK생성 전략을 명시
   - IDENTITY : 트랜잭션 종료 시점에 id에 해당하는 값을 증가 하여 할당
     - 커밋과 상관없이 이미 증가된 값이므로, id값 사이에 공백 발생 가능
     - 주로 MySQL에서 사용
   - SEQUENCE : 시퀀스 객체로 부터 다음 id에 해당하는 값을 얻어 할당
     - 주로 Oracle, PostgreSql, H2에서 사용
   - TABLE : DB의 종류에 상관없이 id를 관리하는 별도의 Table에서 값을 관리
   - AUTO : 각 DB에 적합한 설정 적용

> @Column : 필드와 매핑되는 컬럼에 속성을 지정
 - name : 필드와 매핑할 테이블의 컬럼 이름
 - nullable : 해당 필드와 매핑되는 컬럼의 null 값 허용 여부 설정, false인 경우 DDL 생성 시 NOT NULL 제약조건 생성 (기본값 : true) 
 - length : 문자 길이 제약 조건 설정 (String 필드에만 적용 가능)
 - unique : Entity를 이용한 DDL생성 시 Unique 제약 조건 설정
 - insertable : 트랜잭션 종료 시점에 해당 필드와 매핑되는 컬럼의 삽입 여부를 지정(false = 삽입 대상에 미 포함)
 - updatable : 트랜잭션 종료 시점에 해당 필드와 매핑되는 컬럼의 변경 여부를 지정(false = 변경 대상에 미 포함)
 - columnDefinition : 컬럼에 적용할 속성을 직접 명시
 
> @Transient // Persist Context의 관리 대상에서 제외, DB와의 매핑 없이 오직 객체만이 가지는 속성

---
참고 URL : [github/choi-ys/basic-jpa](https://github.com/choi-ys/Basic_JPA/blob/master/src/main/java/basic/ch05_entity_mapping/domain/entity/ExampleEntity.java)