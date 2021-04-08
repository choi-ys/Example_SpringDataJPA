package io.example.springdatajpa.util.paging;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author : choi-ys
 * @date : 2021/04/08 2:56 오후
 * @Content : Paging 처리에 필요한 Util
 */
public class PagingUtils {
    public static PageRequest createPageRequest(int requestPage, int perPageNum, Sort orderBy) {
        return PageRequest.of((requestPage-1), perPageNum, orderBy);
    }

    @Test
    @DisplayName("Math.random")
    @Disabled
    public void randomNumber(){
        // Given
        for (int i = 0; i < 1000; i++) {
            System.out.println((int)((Math.random() * 30)+20));
        }
    }
}