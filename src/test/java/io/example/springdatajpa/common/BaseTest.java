package io.example.springdatajpa.common;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

/**
 * @author : choi-ys
 * @date : 2021/04/05 1:29 오후
 * @Content : 각 TC에서 공통으로 선언되는 Annotation 설정
 */
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
@Profile("test")
public class BaseTest {
}