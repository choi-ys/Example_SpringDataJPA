package io.example.springdatajpa;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
class SpringDataJpaApplicationTests {

    @Test
    void contextLoads() {
    }

}
