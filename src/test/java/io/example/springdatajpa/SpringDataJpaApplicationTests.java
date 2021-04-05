package io.example.springdatajpa;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;

@SpringBootTest
@Profile("test")
@Disabled
class SpringDataJpaApplicationTests {

    @Test
    void contextLoads() {
    }

}
