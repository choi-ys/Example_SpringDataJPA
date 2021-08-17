package io.example.springdatajpa.repository.ch04_various_query_method_expression;

import io.example.springdatajpa.config.AuditorConfig;
import io.example.springdatajpa.domain.entity.Member;
import io.example.springdatajpa.generator.MemberGenerator;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : choi-ys
 * @date : 2021/08/15 3:22 오후
 * @apiNote :
 */
@DataJpaTest
@DisplayName("[#Ch.05]Query Method Expression")
@ActiveProfiles("test")
@Import(AuditorConfig.class)
class MemberRepositoryTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    MemberRepositoryQueryMethodExpression memberRepositoryQueryMethodExpression;

    @BeforeEach
    public void setUp(){
        memberRepositoryQueryMethodExpression.deleteAll();
        List<Member> memberList = List.of(
                MemberGenerator.createMemberByParam("최용석", 31),
                MemberGenerator.createMemberByParam("이성욱", 31),
                MemberGenerator.createMemberByParam("권성준", 31),
                MemberGenerator.createMemberByParam("기호창", 30),
                MemberGenerator.createMemberByParam("이하은", 29),
                MemberGenerator.createMemberByParam("박재현", 29),
                MemberGenerator.createMemberByParam("전성원", 27)
        );
        memberRepositoryQueryMethodExpression.saveAll(memberList);
        entityManager.flush();
    }
    
    @Test
    @DisplayName("이상(숫자) : GreaterThanEqual")
    public void greaterThanEqualAge(){
        // Given
        int criteriaAge = 30;
    
        // When
        List<Member> expectedGreaterThanEqualResult = memberRepositoryQueryMethodExpression.findByAgeGreaterThanEqual(criteriaAge);

        // Then
        assertAll(
                () -> assertNotEquals(expectedGreaterThanEqualResult.size(), 0),
                () -> expectedGreaterThanEqualResult.stream().forEach(
                        it -> assertTrue(it.getAge() >= criteriaAge)
                )
        );
    }
    
    @Test
    @DisplayName("초과(숫자) : After, GreaterThan")
    public void overAge(){
        // Given
        int criteriaAge = 30;
    
        // When
        List<Member> expectedAfterResult = memberRepositoryQueryMethodExpression.findByAgeAfter(criteriaAge);
        List<Member> expectedGreaterThanResult = memberRepositoryQueryMethodExpression.findByAgeGreaterThan(criteriaAge);
    
        // Then
        assertAll(
                () -> assertNotEquals(expectedAfterResult.size(), 0),
                () -> assertEquals(expectedAfterResult, expectedGreaterThanResult),
                () -> expectedAfterResult.stream().forEach(
                        it -> assertTrue(it.getAge() > criteriaAge)
                )
        );
    }
    
    @Test
    @DisplayName("이하(숫자) : LessThanEqual")
    public void lessThanEqualAge(){
        // Given
        int criteriaAge = 30;

        // When
        List<Member> expectedLessThanEqualResult = memberRepositoryQueryMethodExpression.findByAgeLessThanEqual(criteriaAge);

        // Then
        assertAll(
                () -> assertNotEquals(expectedLessThanEqualResult.size(), 0),
                () -> expectedLessThanEqualResult.stream().forEach(
                        it -> assertTrue(it.getAge() <= criteriaAge)
                )
        );
    }

    @Test
    @DisplayName("미만(숫자) : Before, LessThan")
    public void underAge(){
        // Given
        int criteriaAge = 30;
    
        // When
        List<Member> expectedBeforeResult = memberRepositoryQueryMethodExpression.findByAgeBefore(criteriaAge);
        List<Member> expectedLessThanResult = memberRepositoryQueryMethodExpression.findByAgeLessThan(criteriaAge);
    
        // Then
        assertAll(
                () -> assertNotEquals(expectedBeforeResult.size(), 0),
                () -> assertEquals(expectedBeforeResult, expectedLessThanResult),
                () -> expectedBeforeResult.stream().forEach(
                        it -> assertTrue(it.getAge() < criteriaAge)
                )
        );
    }
    
    @Test
    @DisplayName("범위(숫자) : Between")
    public void betweenAge(){
        // Given
        int startAge = 29;
        int endAge = 30;
    
        // When
        List<Member> expectedBetweenResult = memberRepositoryQueryMethodExpression.findByAgeBetween(startAge, endAge);
    
        // Then
        assertAll(
                () -> assertNotEquals(expectedBetweenResult.size(), 0),
                () -> expectedBetweenResult.stream().forEach(
                        it -> assertTrue(it.getAge() >= 29 && it.getAge() <= 30)
                )
        );
    }

    @Test
    @DisplayName("이상(날짜) : GreaterThanEqual")
    public void greaterThanEqualDate(){
        // Given
        LocalDateTime criteriaDate = LocalDateTime.now().minusDays(1L);

        // When
        List<Member> expectedGreaterThanEqual = memberRepositoryQueryMethodExpression.findByCreatedDateGreaterThanEqual(criteriaDate);

        // Then
        assertAll(
                () -> assertNotEquals(expectedGreaterThanEqual.size(), 0),
                () -> expectedGreaterThanEqual.stream().forEach(
                        it -> assertTrue((it.getCreatedDate().isAfter(criteriaDate)
                                        || it.getCreatedDate().isEqual(criteriaDate))
                        )
                )
        );
    }

    @Test
    @DisplayName("초과(날짜) : After, GreaterThan")
    public void overDate(){
        // Given
        LocalDateTime criteriaDate = LocalDateTime.now().minusDays(1L);

        // When
        List<Member> expectedAfterResult = memberRepositoryQueryMethodExpression.findByCreatedDateAfter(criteriaDate);
        List<Member> expectedGreaterThanResult = memberRepositoryQueryMethodExpression.findByCreatedDateGreaterThan(criteriaDate);

        // Then
        assertAll(
                () -> assertNotEquals(expectedAfterResult.size(), 0),
                () -> assertEquals(expectedAfterResult, expectedGreaterThanResult),
                () -> expectedAfterResult.stream().forEach(
                        it -> assertTrue(it.getCreatedDate().isAfter(criteriaDate))
                )
        );
    }
    
    @Test
    @DisplayName("이하(날짜) : LessThanEqual")
    public void lessThanEqualDate(){
        // Given
        LocalDateTime criteriaDate = LocalDateTime.now().plusDays(1L);

        // When
        List<Member> expectedBeforeResult = memberRepositoryQueryMethodExpression.findByCreatedDateLessThanEqual(criteriaDate);

        // Then
        assertAll(
                () -> assertNotEquals(expectedBeforeResult.size(), 0),
                () -> expectedBeforeResult.stream().forEach(
                        it -> assertTrue((it.getCreatedDate().isBefore(criteriaDate)
                                || it.getCreatedDate().isEqual(criteriaDate))
                        )
                )
        );
    }

    @Test
    @DisplayName("미만(날짜) : Before, LessThan")
    public void underDate(){
        // Given
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1L);

        // When
        List<Member> expectedBeforeResult = memberRepositoryQueryMethodExpression.findByCreatedDateBefore(futureDateTime);
        List<Member> expectedLessThanResult = memberRepositoryQueryMethodExpression.findByCreatedDateLessThan(futureDateTime);

        // Then
        assertAll(
                () -> assertNotEquals(expectedBeforeResult.size(), 0),
                () -> assertEquals(expectedBeforeResult, expectedLessThanResult),
                () -> expectedBeforeResult.stream().forEach(
                        it -> it.getCreatedDate().isBefore(futureDateTime)
                )
        );
    }

    @Test
    @DisplayName("범위(날짜) : Between")
    public void betweenDate(){
        // Given
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(1L);
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(1L);

        // When
        List<Member> expectedBetweenResult = memberRepositoryQueryMethodExpression.findByCreatedDateBetween(startDateTime, endDateTime);

        // Then
        assertAll(
                () -> assertNotEquals(expectedBetweenResult.size(), 0),
                () -> expectedBetweenResult.stream().forEach(
                        it -> assertTrue(it.getCreatedDate().isAfter(startDateTime)
                                        && it.getCreatedDate().isBefore(endDateTime)
                                )
                )
        );
    }
    
    @Test
    @DisplayName("포함 : In")
    public void nameIn(){
        // Given
        List<String> nameList = Lists.newArrayList("박재현", "이성욱");
        nameList.sort(Comparator.naturalOrder());

        // When
        List<Member> expectedInResult = memberRepositoryQueryMethodExpression.findByNameIn(nameList);

        // Then
        assertAll(
                () -> assertNotEquals(expectedInResult.size(), 0),
                () -> assertEquals(expectedInResult.stream()
                        .map(it -> it.getName())
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList()), nameList)
        );
    }

    @Test
    @DisplayName("미 포함 : NotIn")
    public void nameNotIn(){
        // Given
        List<String> nameList = Lists.newArrayList("박재현", "이성욱");
        nameList.sort(Comparator.naturalOrder());

        // When
        List<Member> expectedInResult = memberRepositoryQueryMethodExpression.findByNameNotIn(nameList);

        // Then
        assertAll(
                () -> assertNotEquals(expectedInResult.size(), 0),
                () -> assertEquals(expectedInResult.stream()
                        .map(it -> it.getName())
                        .filter(name -> name.equals("박재현") || name.equals("이성욱"))
                        .collect(Collectors.toList()).size(), 0)
        );
    }

    @Test
    @DisplayName("패턴 검색 : Like")
    public void likeNmae(){
        // Given
        String searchText = "최";

        // When
        List<Member> byNameStartingWith = memberRepositoryQueryMethodExpression.findByNameStartingWith(searchText);
        List<Member> byNameEndingWith = memberRepositoryQueryMethodExpression.findByNameEndingWith(searchText);
        List<Member> byNameContains = memberRepositoryQueryMethodExpression.findByNameContains(searchText);
        List<Member> byNameLike = memberRepositoryQueryMethodExpression.findByNameLike(searchText);

        // Then
        assertAll(
                () -> assertNotEquals(byNameStartingWith.size(), 0),
                () -> assertEquals(byNameEndingWith.size(), 0),
                () -> assertNotEquals(byNameContains.size(), 0),
                () -> assertEquals(byNameLike.size(), 0)
        );
    }
    
    @Test
    @DisplayName("순위 산출 : First, Top, OrderByDesc/Asc")
    public void first(){
        // When
        Member expectedTopMemberOrderByAgeDesc = memberRepositoryQueryMethodExpression.findFirstBy(Sort.by(Sort.Order.desc("age")));
        Member expectedFirstMemberOrderByAgeAsc = memberRepositoryQueryMethodExpression.findFirstByOrderByAgeAsc();
        Member expectedTopMemberOrderByAgeAsc = memberRepositoryQueryMethodExpression.findTopByOrderByAgeAsc();
        List<Member> expectedTop2MemberListOrderByAgeDesc = memberRepositoryQueryMethodExpression.findTop2ByOrderByAgeDesc();

        // Then
        assertAll(
                () -> assertNotNull(expectedTopMemberOrderByAgeDesc),
                () -> assertNotNull(expectedFirstMemberOrderByAgeAsc),
                () -> assertEquals(expectedTopMemberOrderByAgeAsc, expectedTopMemberOrderByAgeAsc),
                () -> assertEquals(expectedTop2MemberListOrderByAgeDesc.size(), 2)
        );
    }
}
