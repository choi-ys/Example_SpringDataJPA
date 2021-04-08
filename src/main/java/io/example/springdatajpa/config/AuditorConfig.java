package io.example.springdatajpa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

/**
 * @author : choi-ys
 * @date : 2021/04/08 6:32 오후
 * @Content : Spring Data Jpa Auditing 설정 부
 */
@Configuration
@EnableJpaAuditing
public class AuditorConfig {

    @Bean
    public AuditorAware<String> auditorProvider(){
        /**
         * JWT의 Principal을 이용한 Auditing 정보 설정
         */
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return () -> Optional.of(UUID.randomUUID().toString());
    }
}