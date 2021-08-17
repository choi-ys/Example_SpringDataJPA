package io.example.springdatajpa.repository.ch08_auditing_for_entity_metadata.domain.common;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

/**
 * @author : choi-ys
 * @date : 2021/04/08 5:45 오후
 * @Content : 엔티티 생성,변경 이력을 관리하기 위한 순수 JPA의 MetaData 설정 객체
 */
@MappedSuperclass // 해당 class의 속성만 Entity에 상속(JPA의 상속)하기 위해 명시
@Getter
public class JpaBaseEntity {

    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    /**
     * EntityManager의 Persist()발생 전 이벤트
     */
    @PrePersist
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createdDate = now;
        updatedDate = now;
    }

    /**
     * EntityManager의 dirtyChecking/merge() 발생 전 이벤트
     */
    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }
}