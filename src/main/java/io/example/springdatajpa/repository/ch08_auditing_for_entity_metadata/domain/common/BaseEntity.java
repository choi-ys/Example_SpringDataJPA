package io.example.springdatajpa.repository.ch08_auditing_for_entity_metadata.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import javax.persistence.Column;

/**
 * @author : choi-ys
 * @date : 2021/04/08 6:26 오후
 * @Content : 엔티티 등록자/수정자, 등록일/수정일 이력을 관리하기 위한 MetaData 설정 객체
 *  - Spring Data Jpa의 Auditing 설정
 *    - 주의사항 : 아래 사항을 필수로 설정해야 Spring Data Auditing기능을 사용할 수 있다.
 *      - @EnableJpaAuditing 설정을 명시하여야 Spring Data Jpa의 Auditing 사용 가능 : {@link io.example.springdatajpa.config.AuditorConfig}
 *      - @EntityListeners(AuditingEntityListener.class) : {@link BaseTimeEntity}
 *      - @MappedSuperclass // 해당 class의 속성만 Entity에 상속(JPA의 상속)하기 위해 명시 : {@link BaseTimeEntity}
 *  - Application에서 사용하는 사용자 정보를 이용한 등록자(CreatedBy)/수정자(LastModifiedBy) 정보 설정
 *    - {@link io.example.springdatajpa.config.AuditorConfig} : AuditorAware<String> auditorProvider
 *  - 등록일/수정일만 자동 설정하고 싶은경우 : Entity에 {@link BaseTimeEntity}를 상속
 *  - 등록일/수정일, 등록자/수정자를 자동 설정 하고 싶은경우 : Entity에 {@link BaseEntity}를 상속
 */
@Getter
public class BaseEntity extends BaseTimeEntity{

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;
}