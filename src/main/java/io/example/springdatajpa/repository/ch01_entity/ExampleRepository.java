package io.example.springdatajpa.repository.ch01_entity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author : choi-ys
 * @date : 2021-08-17 오후 3:43
 */
public interface ExampleRepository extends JpaRepository<ExampleEntity, Long> {
}
