package io.example.springdatajpa.domain.dto.paging;

import io.example.springdatajpa.domain.dto.slice.Slicing;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import static lombok.AccessLevel.PROTECTED;

/**
 * @author : choi-ys
 * @date : 2021/04/08 3:20 오후
 * @Content : Spring Data Jpa의 Page<T> 반환 타입을 가공
 */
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Paging<T> extends Slicing {
    private int totalPageCount;
    private long totalElementCount;

    public Paging(Page page) {
        super(page);
        this.totalPageCount = page.getTotalPages();
        this.totalElementCount = page.getTotalElements();
    }
}