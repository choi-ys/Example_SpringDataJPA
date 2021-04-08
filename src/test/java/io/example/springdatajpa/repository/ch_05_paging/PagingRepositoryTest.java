package io.example.springdatajpa.repository.ch_05_paging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.example.springdatajpa.common.BaseTest;
import io.example.springdatajpa.domain.dto.paging.MemberPagingDtoWrap;
import io.example.springdatajpa.domain.dto.slice.MemberSliceDtoWrap;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.domain.entity.Team;
import io.example.springdatajpa.generator.MemberGenerator;
import io.example.springdatajpa.generator.TeamGenerator;
import io.example.springdatajpa.util.paging.PagingUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.annotation.Resource;

/**
 * @author : choi-ys
 * @date : 2021/04/08 2:19 오후
 * @Content : Spring Data Jpa를 이용한 페이징 처리 Test
 */
@DisplayName("PagingRepository:Member")
@Import({MemberGenerator.class, TeamGenerator.class})
class PagingRepositoryTest extends BaseTest {

    @Resource
    MemberGenerator memberGenerator;

    @Resource
    TeamGenerator teamGenerator;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    PagingRepository pagingRepository;

    @Test
    @DisplayName("Paging")
    public void paging() throws JsonProcessingException {
        // Given
        for (int i = 0; i < 500; i++) {
            memberGenerator.savedMemberByParam("최용석:"+i, ((int)((Math.random() * 30)+20)));
        }

        Team team = teamGenerator.savedTeam();
        for (int i = 0; i < 500; i++) {
            memberGenerator.savedMemberWithTeam("이성욱:"+i, ((int)((Math.random() * 30)+20)), team);
        }

        int ageCriteria = 30;
        int requestPage = 1;
        int perPageNum = 10;
        String sortProperties = "name";
        Sort orderBy = Sort.by(Sort.Direction.ASC, sortProperties);

        PageRequest pageRequest = PagingUtils.createPageRequest(requestPage, perPageNum, orderBy);

        // When
        Page<Member> memberWithTeamByAge = pagingRepository.findMemberWithTeamPageListByAge(ageCriteria, pageRequest);

        // Then
        System.out.println("================================================================");
        System.out.println("getTotalPages : " + memberWithTeamByAge.getTotalPages());
        System.out.println("getTotalElements : " + memberWithTeamByAge.getTotalElements());
        System.out.println("getNumber : " + memberWithTeamByAge.getNumber());
        System.out.println("getNumberOfElements : " + memberWithTeamByAge.getNumberOfElements());
        System.out.println("getSize : " + memberWithTeamByAge.getSize());
        System.out.println("isFirst : " + memberWithTeamByAge.isFirst());
        System.out.println("isLast : " + memberWithTeamByAge.isLast());
        System.out.println("hasNext : " + memberWithTeamByAge.hasNext());
        System.out.println("hasPrevious : " + memberWithTeamByAge.hasPrevious());
        System.out.println("================================================================");
        MemberPagingDtoWrap memberPagingDtoWrap = new MemberPagingDtoWrap(memberWithTeamByAge);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberPagingDtoWrap));
        System.out.println("================================================================");

        while (memberPagingDtoWrap.isHasNextPage()){
            requestPage = requestPage+1;
            PageRequest nextRequest = PagingUtils.createPageRequest(requestPage, perPageNum, orderBy);
            memberWithTeamByAge = pagingRepository.findMemberWithTeamPageListByAge(ageCriteria, nextRequest);
            memberPagingDtoWrap = new MemberPagingDtoWrap(memberWithTeamByAge);
            if(memberPagingDtoWrap.isLastPage()){
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberPagingDtoWrap));
            }
        }
    }

    @Test
    @DisplayName("Slicing")
    @Rollback(value = false)
    public void slicing() throws JsonProcessingException {
        // Given
        for (int i = 0; i < 500; i++) {
            memberGenerator.savedMemberByParam("최용석:"+i, ((int)((Math.random() * 30)+20)));
        }

        Team team = teamGenerator.savedTeam();
        for (int i = 0; i < 500; i++) {
            memberGenerator.savedMemberWithTeam("이성욱:"+i, ((int)((Math.random() * 30)+20)), team);
        }

        int ageCriteria = 30;
        int requestPage = 2;
        int perPageNum = 10;
        String sortProperties = "name";
        Sort orderBy = Sort.by(Sort.Direction.ASC, sortProperties);

        PageRequest pageRequest = PagingUtils.createPageRequest(requestPage, perPageNum, orderBy);

        // When
        Slice<Member> memberWithTeamSliceListByAge = pagingRepository.findMemberWithTeamSliceListByAge(ageCriteria, pageRequest);

        // Then
        System.out.println("================================================================");
        System.out.println("getNumber : " + memberWithTeamSliceListByAge.getNumber());
        System.out.println("getNumberOfElements : " + memberWithTeamSliceListByAge.getNumberOfElements());
        System.out.println("getSize : " + memberWithTeamSliceListByAge.getSize());
        System.out.println("isFirst : " + memberWithTeamSliceListByAge.isFirst());
        System.out.println("isLast : " + memberWithTeamSliceListByAge.isLast());
        System.out.println("hasNext : " + memberWithTeamSliceListByAge.hasNext());
        System.out.println("hasPrevious : " + memberWithTeamSliceListByAge.hasPrevious());
        System.out.println("================================================================");
        MemberSliceDtoWrap memberSliceDtoWrap = new MemberSliceDtoWrap(memberWithTeamSliceListByAge);
        System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberSliceDtoWrap));
        System.out.println("================================================================");

        while (memberSliceDtoWrap.isHasNextPage()){
            requestPage = requestPage+1;
            PageRequest nextRequest = PagingUtils.createPageRequest(requestPage, perPageNum, orderBy);
            memberWithTeamSliceListByAge = pagingRepository.findMemberWithTeamSliceListByAge(ageCriteria, nextRequest);
            memberSliceDtoWrap = new MemberSliceDtoWrap(memberWithTeamSliceListByAge);
            if(memberSliceDtoWrap.isLastPage()){
                System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(memberSliceDtoWrap));
            }
        }
    }
}
