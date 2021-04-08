package io.example.springdatajpa.domain.dto.paging;

import io.example.springdatajpa.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author : choi-ys
 * @date : 2021/04/08 3:24 오후
 * @Content : 회원/팀 Entity의 Paging 반환을 위한 DTO
 */
@Getter @NoArgsConstructor(access = PROTECTED)
public class MemberPagingDtoWrap extends Paging{
    private List<MemberDto> memberDtoList = new ArrayList<>();

    public MemberPagingDtoWrap(Page page) {
        super(page);
        List<Member> content = page.getContent();
        for (Member member : content) {
            this.memberDtoList.add(new MemberDto(member));
        }
    }
}