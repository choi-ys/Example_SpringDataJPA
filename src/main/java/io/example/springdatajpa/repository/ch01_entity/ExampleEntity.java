package io.example.springdatajpa.repository.ch01_entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static lombok.AccessLevel.PROTECTED;

/** @Entity : JPA에서 관리하는 엔티티 클래스 지정 시 사용, 해당 객체가 Persist context의 관리 대상 객체임을 명시
 * - name : JPA에서 사용할 엔티티 이름, 주로 Class이름이 중복일 경우 이를 구분하기 위해서 사용하며 기본값 사용 권장
 *   - 기본값 : 클래스 이름 그대로 사용
 */
@Entity(name = "member")

/** @Table
 *  - name : Entity와 매핑되는 DB의 Table명 지정, 생략시 @Entity의 name속성 값의 테이블과 매핑
 *  - catalog : catalog를 제공하는 DB의 catalog와 매핑
 *    - catalog = DB 개체들에 대한 정의를 담고 있는 메타데이터, 데이터 사전(Data Dictionary)라고도 표현
 *      (테이블, 뷰, synonym, 값 범위, 인덱스 사용자 그룹 등의 정보를 포함)
 *  - schema : schema를 제공하는 DB의 schema와 매핑
 *    - schema : 테이블의 형태를 표현한 메타 데이터
 *      (DB에 저장되는 데이터와 구조, 관계, 테이블의 속성 등의 정보를 포함)
 *  - uniqueConstraints : Entity를 이용한 DDL 생성 시, 해당 설정의 unique 제약조건을 생성, 2개 이상의 복합 unique 제약조건도 가능
 *    - name : Unique 제약 조건 명
 *    - columnNames : Unique 제약조건이 적용될 필드
 *  - indexes : Entity의 필드와 매핑되는 컬럼에 인덱싱 설정
 */
@Table(
        name = "example_tb", // Entity와 매핑되는 DB의 Table명
        uniqueConstraints = @UniqueConstraint( // uniqueConstraints :
                name = "EMAIL_UNIQUE",
                columnNames = "email"
        ),
        indexes = { // Entity에 명시된 항목과 매핑되는 컬럼에 인덱싱을 설정
                @Index(columnList = "createdDate")
        },
)
/** @SequenceGenerator : DDL생성 시 해당 설정을 이용하여 Sequence객체 생성 및 매핑
 *  - name : Sequence를 생성하는 SequenceGenerator의 이름을 지정
 *  - sequenceName : 생성 또는 매핑할 Sequence 이름을 지정
 *  - initialValue : 최초 생성 시 Sequence의 초기값을 지정
 *  - allocationSize : 다음 Sequence의 증가값 지정
 */
@SequenceGenerator(
        name = "EXAMPLE_ENTITY_SEQ_GENERATOR",
        sequenceName = "EXAMPLE_ENTITY_SEQ",
        initialValue = 1,
        allocationSize = 1
)
@Getter
@NoArgsConstructor(access = PROTECTED)
public class ExampleEntity {

    @Id // Record를 구분하기 위한 PK를 표현하는 Annotations
    /** @GeneratedValue
     *  - generator
     *  - strategy :
     *    - IDENTITY : 트랜잭션 종료 시점에 id에 해당하는 값을 증가 하여 할당
     *      - 참고 : 
     *        - 커밋과 상관없이 이미 증가된 값이므로, id값 사이에 공백이 생길 수 있다.
     *        - 주로 MySQL에서 사용
     *    - SEQUENCE : 시퀀스 객체로 부터 다음 id에 해당하는 값을 얻어 할당
     *      - 참고
     *        - 주로 Oracle, PostgreSql, H2에서 사용
     *    - TABLE : DB의 종류에 상관없이 id를 관리하는 별도의 Table에서 값을 관리
     *    - AUTO : 각 DB에 적합한 설정 적용
     */
    @GeneratedValue(generator = "EXAMPLE_ENTITY_SEQ_GENERATOR")
    @Column(name = "example_no")
    private Long no;

    /** @Column : 필드와 매핑되는 컬럼에 속성을 지정
     *  - name : 해당 컬럼의 이름을 지정
     *  - nullable : Entity를 이용한 DDL생성 시 해당 필드와 매핑되는 컬럼의 널 여부를 지정
     *  - length : 해당 컬럼의 길이를 지정
     *  - unique : Entity를 이용한 DDL생성 시 Unique 제약 조건 설정
     *  - insertable : 트랜잭션 종료 시점에 해당 필드와 매핑되는 컬럼의 삽입 여부를 지정(false = 삽입 대상에 미 포함)
     *  - updatable : 트랜잭션 종료 시점에 해당 필드와 매핑되는 컬럼의 변경 여부를 지정(false = 변경 대상에 미 포함)
     */
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Transient // Persist Context의 관리 대상에서 제외, DB와의 매핑 없이 오직 객체만이 가지는 속성> @GeneratedValue : PK생성에 관련된 설정
    private String onlyObjectField;

    /** @Enumerated : Java의 Enum객체를 필드로 매핑
     *  - EnumType.ORDINAL : Java Enum객체에 명시된 필드의 순서로 매핑
     *  - EnumType.STRING : Java Enum객체에 명시된 필드의 String값으로 매핑(권장)
     */
    @Enumerated(EnumType.STRING)
    private ExampleEnum exampleEnum;

    // * --------------------------------------------------------------
    // * Header : 도메인 생성
    // * @author : choi-ys
    // * @date : 2021-08-17 오후 3:54
    // * --------------------------------------------------------------
    public ExampleEntity(String email, String onlyObjectField, ExampleEnum exampleEnum) {
        this.email = email;
        this.onlyObjectField = onlyObjectField;
        this.exampleEnum = exampleEnum;
    }

    // * --------------------------------------------------------------
    // * Header : Entity의 7가지 Listener
    // * @author : choi-ys
    // * @date : 2021-08-17 오전 2:34
    // * --------------------------------------------------------------
    @PrePersist // insert 호출 전 실행
    public void prePersist() {
        System.out.println(">>> prePersist");
    }

    @PostPersist // insert 호출 후 실행
    public void postPersist() {
        System.out.println(">>> postPersist");
    }

    @PreUpdate // merge 호출 전 실행
    public void preUpdate() {
        System.out.println(">>> preUpdate");
    }

    @PostUpdate // merge 호출 후 실행
    public void postUpdate() {
        System.out.println(">>> postUpdate");
    }

    @PreRemove // delete 호출 전 실행
    public void preRemove() {
        System.out.println(">>> preRemove");
    }

    @PostRemove // delete 호출 후 실행
    public void postRemove() {
        System.out.println(">>> postRemove");
    }

    @PostLoad // select 직 후 실행
    public void postLoad() {
        System.out.println(">>> postLoad");
    }
}
