package io.example.springdatajpa.repository.ch01_member_crud;

import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.generator.MemberGenerator;
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
 * @date : 2021/04/05 11:09 오전
 * @Content : 순수 JPA Repository Test Case
 */
@DisplayName("CrudRepository[JPA]:Member")
class MemberCrudRepositoryJpaTest extends BaseTest {

    @Resource
    MemberCrudRepositoryJpa memberCrudRepositoryJpa;

    @Test
    @DisplayName("Save")
//    @Rollback(value = false)
    public void saveMember(){
        // Given
        Member member = MemberGenerator.createMember();

        // When
        Member savedMember = this.memberCrudRepositoryJpa.save(member);

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
        this.memberCrudRepositoryJpa.save(member);

        // When
        Optional<Member> optionalMember = memberCrudRepositoryJpa.findById(member.getNo());
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
        Member savedMember = this.memberCrudRepositoryJpa.save(member);

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
        Member savedMember = this.memberCrudRepositoryJpa.save(member);
        assertEquals(savedMember, member);

        // When
        this.memberCrudRepositoryJpa.delete(member);

        // Then
        NoSuchElementException noSuchElementException = assertThrows(NoSuchElementException.class, () -> {
            this.memberCrudRepositoryJpa.findById(member.getNo()).orElseThrow();
        });
        assertEquals(noSuchElementException.getMessage(), "No value present");
    }

    @Test
    @DisplayName("Count")
//    @Rollback(value = false)
    public void memberCount(){
        // Given
        Member firstMember = MemberGenerator.createMemberByParam("최용석", 31);
        Member secondMember = MemberGenerator.createMemberByParam("이성욱", 31);
        Member thirdMember = MemberGenerator.createMemberByParam("박재현", 29);
        this.memberCrudRepositoryJpa.save(firstMember);
        this.memberCrudRepositoryJpa.save(secondMember);
        this.memberCrudRepositoryJpa.save(thirdMember);

        // When
        long memberCount = this.memberCrudRepositoryJpa.count();

        // Then
        assertThat(memberCount).isNotZero();
        assertEquals(memberCount, 3);
    }

    @Test
    @DisplayName("FindAll")
    public void findAllMember(){
        // Given
        Member firstMember = MemberGenerator.createMemberByParam("최용석", 31);
        Member secondMember = MemberGenerator.createMemberByParam("이성욱", 31);
        Member thirdMember = MemberGenerator.createMemberByParam("박재현", 29);
        this.memberCrudRepositoryJpa.save(firstMember);
        this.memberCrudRepositoryJpa.save(secondMember);
        this.memberCrudRepositoryJpa.save(thirdMember);

        // When
        List<Member> savedMemberList = this.memberCrudRepositoryJpa.findAll();

        // Then
        assertThat(savedMemberList.size()).isNotZero();
        assertThat(savedMemberList.contains(firstMember));
        assertThat(savedMemberList.contains(secondMember));
        assertThat(savedMemberList.contains(thirdMember));
    }
}