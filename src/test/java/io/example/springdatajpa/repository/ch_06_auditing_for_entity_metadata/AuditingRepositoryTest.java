package io.example.springdatajpa.repository.ch_06_auditing_for_entity_metadata;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.generator.MemberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;
import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author : choi-ys
 * @date : 2021/04/08 5:54 오후
 * @Content : 각 Entity의 생성/변경 이력을 관리하기 위해 순수 JPA와 Spring Data Jpa의 제공 기능 확인을 위한 Repository
 *  - Test방법 : {@link Member} Entity의 상속 설정을 바꾸어 아래 생성/수정 Test Case를 수행
 *    - 순수 Jpa를 이용한 Auditing : public class Member extends JpaBaseEntity -> {@link Member}
 *    - Spring Data Jpa를 이용한 등록일/수정일 관리 : public class Member extends BaseTimeEntity -> {@link Member}
 *    - Spring Data Jpa를 이용한 등록자/수정자, 등록일/수정일 관리: public class Member extends BaseEntity -> {@link Member}
 */
@DisplayName("AuditingRepository:Member")
@Import({MemberGenerator.class})
class AuditingRepositoryTest extends BaseTest {

    @Resource
    MemberGenerator memberGenerator;

    @Autowired
    EntityManager entityManager;

    @Autowired
    AuditingRepository auditingRepository;

    @Test
    @DisplayName("Entity 생성시 Auditing")
//    @Rollback(value = false)
    public void AuditingForInsert(){
        // Given
        Member member = MemberGenerator.createMember();

        // When
        Member savedMember = auditingRepository.save(member);

        entityManager.flush();

        // Then
        assertEquals(savedMember, member);
        assertThat(savedMember.getCreatedDate()).isNotNull();
        assertEquals(savedMember.getUpdatedDate(), savedMember.getUpdatedDate());
    }

    @Test
    @DisplayName("Entity 수정시 Auditing")
//    @Rollback(value = false)
    public void AuditingForUpdate() throws InterruptedException {
        // Given
        Member savedMember = memberGenerator.savedMember();

        // When
        Optional<Member> optionalMember = auditingRepository.findById(savedMember.getNo());
        Member selectedMember = optionalMember.orElseThrow();
        Thread.sleep(1000);
        selectedMember.changeMemberName("윤용석");

        entityManager.flush();
        entityManager.clear();

        // Then
        assertNotEquals(selectedMember.getCreatedDate(), selectedMember.getUpdatedDate());
    }
}