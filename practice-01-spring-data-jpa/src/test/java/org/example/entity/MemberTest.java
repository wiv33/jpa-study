package org.example.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author pilseong.ko
 */
@SpringBootTest
@Transactional
@Rollback(value = false)
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    void testEntity() {
        var teamA = new Team("teamA");
        var teamB = new Team("teamB");

        List<Member> memberList = List.of(new Member("member1", 10, teamA),
                                          new Member("member2", 20, teamA),
                                          new Member("member3", 30, teamB),
                                          new Member("member4", 40, teamB));

        memberList.forEach(em::persist);

        em.flush();
        em.clear();


        List<Member> resultList = em.createQuery("select m from Member as m", Member.class).getResultList();
        resultList.forEach(System.out::println);

    }
}
