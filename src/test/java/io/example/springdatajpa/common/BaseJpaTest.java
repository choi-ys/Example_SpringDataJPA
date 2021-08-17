package io.example.springdatajpa.common;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestConstructor;

import javax.persistence.EntityManager;

/**
 * @author : choi-ys
 * @date : 2021-08-17 오후 3:44
 */
@DataJpaTest
@TestMethodOrder(MethodOrderer.MethodName.class)
@Disabled
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class BaseJpaTest {

    @Autowired
    EntityManager entityManager;

    public void flush(){
        entityManager.flush();
    }

    public void clear(){
        entityManager.clear();
    }

    public void flushAndClearPersistContext() {
        flush();
        clear();
    }

}


