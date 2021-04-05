package io.example.springdatajpa.repository;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.generator.MemberGenerator;
import io.example.springdatajpa.repository.ch01_member_crud.MemberCrudRepositorySpringDataJpa;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.annotation.Resource;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author : choi-ys
 * @date : 2021/04/05 10:58 오전
 * @Content : Spring Data Jpa Repository Test
 */
@DisplayName("CrudRepository[SpringDataJpa]:Member")
class MemberCrudRepositorySpringDataJpaTest extends BaseTest {

    @Resource
    MemberCrudRepositorySpringDataJpa memberCrudRepositorySpringDataJpa;

    @Test
    @DisplayName("Save")
//    @Rollback(value = false)
    public void saveMember(){
        // Given
        Member member = MemberGenerator.createMember();

        // When
        Member savedMember = this.memberCrudRepositorySpringDataJpa.save(member);

        // Then
        assertEquals(savedMember, member);
        assertEquals(savedMember.getNo(), member.getNo());
        assertEquals(savedMember.getName(), member.getName());
    }

    @Test
    @DisplayName("FindById")
//    @Rollback(value = false)
    public void findMemberById(){
        // Given
        Member member = MemberGenerator.createMember();
        this.memberCrudRepositorySpringDataJpa.save(member);

        // When
        Optional<Member> optionalMember = this.memberCrudRepositorySpringDataJpa.findById(member.getNo());
        Member selectedMember = optionalMember.orElseThrow();

        // Then
        assertEquals(selectedMember, member);
    }

    @Test
    @DisplayName("Update")
//    @Rollback(value = false)
    public void updateMember(){
        // Given
        Member member = MemberGenerator.createMember();
        Member savedMember = this.memberCrudRepositorySpringDataJpa.save(member);

        // When
        String changedMemberName = "최용";
        member.changeMemberName(changedMemberName);

        // Then
        assertEquals(savedMember, member);
        assertEquals(member.getName(), changedMemberName);
    }

    @Test
    @DisplayName("Delete")
//    @Rollback(value = false)
    public void deleteMember(){
        // Given
        Member member = MemberGenerator.createMember();

        Member savedMember = this.memberCrudRepositorySpringDataJpa.save(member);
        assertEquals(savedMember, member);

        // When
        this.memberCrudRepositorySpringDataJpa.delete(savedMember);

        // Then
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> {
            this.memberCrudRepositorySpringDataJpa.findById(member.getNo()).orElseThrow();
        });
        assertEquals(noSuchElementException.getMessage(), "No value present");
    }

    @Test
    @DisplayName("Count")
//    @Rollback(value = false)
    public void memberCount(){
        // Given
        Member firstMember = MemberGenerator.createMemberByMemberName("최용석", 31);
        Member secondMember = MemberGenerator.createMemberByMemberName("이성욱", 31);
        Member thirdMember = MemberGenerator.createMemberByMemberName("박재현", 29);
        this.memberCrudRepositorySpringDataJpa.save(firstMember);
        this.memberCrudRepositorySpringDataJpa.save(secondMember);
        this.memberCrudRepositorySpringDataJpa.save(thirdMember);

        // When
        long memberCount = this.memberCrudRepositorySpringDataJpa.count();

        // Then
        assertThat(memberCount).isNotZero();
        assertEquals(memberCount, 3);
    }

    @Test
    @DisplayName("FindAll")
    public void findAllMember(){
        // Given
        Member firstMember = MemberGenerator.createMemberByMemberName("최용석", 31);
        Member secondMember = MemberGenerator.createMemberByMemberName("이성욱", 31);
        Member thirdMember = MemberGenerator.createMemberByMemberName("박재현", 29);
        this.memberCrudRepositorySpringDataJpa.save(firstMember);
        this.memberCrudRepositorySpringDataJpa.save(secondMember);
        this.memberCrudRepositorySpringDataJpa.save(thirdMember);

        // When
        List<Member> savedMemberList =this.memberCrudRepositorySpringDataJpa.findAll(); // JPQL 실행 전 flush 발생

        // Then
        assertThat(savedMemberList.size()).isNotZero();
        assertThat(savedMemberList.contains(firstMember));
        assertThat(savedMemberList.contains(secondMember));
        assertThat(savedMemberList.contains(thirdMember));
        assertThat(savedMemberList.contains(thirdMember));
    }
}