package io.example.springdatajpa.repository.ch04_various_return_type;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.generator.MemberGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @author : choi-ys
 * @date : 2021/04/08 11:01 오전
 * @Content : Spring Data Jpa의 다양한 return type Test
 */
@Import(MemberGenerator.class)
@DisplayName("ReturnTypeRepository[SpringDataJpa]:Member")
class ReturnTypeRepositoryTest extends BaseTest {

    @Resource
    MemberGenerator memberGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ReturnTypeRepository returnTypeRepository;

    @Test
    @DisplayName("Return:Member")
    public void returnMember(){
        // Given
        Member savedMember = memberGenerator.savedMember();

        // When
        Member selectedMember = returnTypeRepository.findMemberByNo(savedMember.getNo());

        // Then
        assertEquals(savedMember.getNo(), selectedMember.getNo());
    }

    @Test
    @DisplayName("Return:Optional<Member>")
    public void returnOptionalMember(){
        // Given
        Member savedMember = memberGenerator.savedMember();

        // When
        Optional<Member> optionalMember = returnTypeRepository.findOptionalMemberByNo(savedMember.getNo());
        Member selectedMember = optionalMember.orElseThrow();

        // Then
        assertEquals(selectedMember.getNo(), savedMember.getNo());
    }

    @Test
    @DisplayName("Return:List<Member>")
    public void returnMemberList(){
        // Given : Member list save when before each test
        this.memberGenerator.savedMemberByParam("이성욱", 30);
        this.memberGenerator.savedMemberByParam("기호창", 30);
        this.memberGenerator.savedMemberByParam("권성준", 30);
        this.memberGenerator.savedMemberByParam("최용석", 30);
        this.memberGenerator.savedMemberByParam("이하은", 28);
        this.memberGenerator.savedMemberByParam("박재현", 28);
        this.memberGenerator.savedMemberByParam("전성원", 25);

        int ageCriteria = 30;

        // When
        List<Member> memberListByAge = returnTypeRepository.findMemberListByAge(ageCriteria);

        // Then
        assertNotEquals(memberListByAge.size(), 0);
        assertEquals(memberListByAge.size(), 4);
    }

    @Test
    @DisplayName("Return:Page<Member>")
    public void returnMemberPageList() throws JsonProcessingException {
        // Given
        this.memberGenerator.savedMemberByParam("이성욱", 30);
        this.memberGenerator.savedMemberByParam("기호창", 30);
        this.memberGenerator.savedMemberByParam("권성준", 30);
        this.memberGenerator.savedMemberByParam("최용석", 30);
        this.memberGenerator.savedMemberByParam("이하은", 28);
        this.memberGenerator.savedMemberByParam("박재현", 28);
        this.memberGenerator.savedMemberByParam("전성원", 25);

        int ageCriteria = 30;
        int requestPage = 0;
        int perPageNum = 3;
        String sortProperties = "name";

        PageRequest pageRequest = createPageRequest(requestPage, perPageNum, sortProperties);

        // When
        Page<Member> memberPageListByAge = returnTypeRepository.findMemberPageListByAge(ageCriteria, pageRequest);
        List<Member> content = memberPageListByAge.getContent();

        // Then
        System.out.println("================================================================");
        System.out.println(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberPageListByAge));
        System.out.println("================================================================");

        assertEquals(memberPageListByAge.getTotalPages(), 2); // 전체 페이지 수
        assertEquals(memberPageListByAge.getNumber(), 0); // 현재 페이지 index
        assertEquals(memberPageListByAge.getNumberOfElements(), 3); // 현재 페이지에 조회된 element 수
        assertEquals(memberPageListByAge.getTotalElements(), 4); // 전체 element 수
        assertEquals(memberPageListByAge.getSize(), perPageNum); // 한 페이지당 조회 갯수

        assertEquals(memberPageListByAge.isFirst(), true); // 현재 페이지의 첫 페이지 여부
        assertEquals(memberPageListByAge.isLast(), false); // 현재 페이지의 마지막 페이지 여부
        assertEquals(memberPageListByAge.hasNext(), true); // 다음 페이지 존재 여부
        assertEquals(memberPageListByAge.hasPrevious(), false); // 이전 페이지 존재 여부
    }

    @Test
    @DisplayName("Return:Slice<Member>")
    public void returnMemberSliceList() throws JsonProcessingException {
        // Given
        this.memberGenerator.savedMemberByParam("이성욱", 30);
        this.memberGenerator.savedMemberByParam("기호창", 30);
        this.memberGenerator.savedMemberByParam("권성준", 30);
        this.memberGenerator.savedMemberByParam("최용석", 30);
        this.memberGenerator.savedMemberByParam("이하은", 28);
        this.memberGenerator.savedMemberByParam("박재현", 28);
        this.memberGenerator.savedMemberByParam("전성원", 25);

        int ageCriteria = 30;
        int requestPage = 0;
        int perPageNum = 3;
        String sortProperties = "name";

        PageRequest pageRequest = createPageRequest(requestPage, perPageNum, sortProperties);

        // When
        Slice<Member> memberSliceListByAge = returnTypeRepository.findMemberSliceListByAge(ageCriteria, pageRequest);
        List<Member> content = memberSliceListByAge.getContent();

        // Then
        System.out.println("================================================================");
        System.out.println(this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberSliceListByAge));
        System.out.println("================================================================");

        assertEquals(memberSliceListByAge.getNumber(), 0); // 현재 페이지 index
        assertEquals(memberSliceListByAge.getNumberOfElements(), 3); // 현재 페이지에 조회된 element 수
        assertEquals(memberSliceListByAge.getSize(), perPageNum); // 한 페이지당 조회 갯수

        assertEquals(memberSliceListByAge.isFirst(), true); // 현재 페이지의 첫 페이지 여부
        assertEquals(memberSliceListByAge.isLast(), false); // 현재 페이지의 마지막 페이지 여부
        assertEquals(memberSliceListByAge.hasNext(), true); // 다음 페이지 존재 여부
        assertEquals(memberSliceListByAge.hasPrevious(), false); // 이전 페이지 존재 여부
    }

    private PageRequest createPageRequest(int requestPage, int perPageNum, String sortProperties) {
        return PageRequest.of(requestPage, perPageNum, Sort.by(Sort.Direction.DESC, sortProperties));
    }
}