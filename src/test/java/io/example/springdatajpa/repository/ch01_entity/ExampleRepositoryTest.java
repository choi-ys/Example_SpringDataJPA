package io.example.springdatajpa.repository.ch01_entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author : choi-ys
 * @date : 2021-08-17 오후 3:44
 */
@DataJpaTest
@ActiveProfiles("test")
class ExampleRepositoryTest {

    @Autowired
    ExampleRepository exampleRepository;

    @Test
    @DisplayName("save")
    public void save(){
        // Given
        ExampleEntity exampleEntity = new ExampleEntity();

        // When

        // Then
    }
}