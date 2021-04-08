package io.example.springdatajpa.domain.dto.slice;

import io.example.springdatajpa.domain.dto.paging.MemberDto;
import io.example.springdatajpa.domain.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author : choi-ys
 * @date : 2021/04/08 4:38 오후
 * @Content : 회원/팀 Entity의 Slice 반환을 위한 DTO
 */
@Getter @NoArgsConstructor(access = PROTECTED)
public class MemberSliceDtoWrap extends Slicing{
    private List<MemberDto> memberDtoList = new ArrayList<>();

    public MemberSliceDtoWrap(Slice slice) {
        super(slice);
        List<Member> content = slice.getContent();
        for (Member member : content) {
            this.memberDtoList.add(new MemberDto(member));
        }
    }
}