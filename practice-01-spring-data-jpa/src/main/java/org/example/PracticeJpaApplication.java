package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pilseong.ko
 */
@SpringBootApplication
public class PracticeJpaApplication {
    public static void main(String[] args) {
        SpringApplication.run(PracticeJpaApplication.class, args);
    }

/*
    @Bean
    JPAQueryFactory initQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
*/
}
