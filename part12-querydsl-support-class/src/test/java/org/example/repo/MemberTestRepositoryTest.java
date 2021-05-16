package org.example.repo;

import org.example.dto.MemberSearchCondition;
import org.example.entity.Member;
import org.example.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pilseong.ko
 */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
class MemberTestRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberTestRepository memberTestRepository;

    @BeforeEach
    void setUp() {
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = new Member("member1", 10, teamA);
        Member member2 = new Member("member2", 20, teamA);
        Member member3 = new Member("member3", 30, teamB);
        Member member4 = new Member("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);
    }

    @Test
    void testBasicSelect() {
        List<Member> members = memberTestRepository.basicSelect();
        assertEquals(4, members.size());
    }

    @Test
    void testBasicSelectFrom() {
        List<Member> members = memberTestRepository.basicSelectFrom();
        assertEquals(4, members.size());
    }

    @Test
    void testApplyPagination() {
        PageRequest pageRequest = PageRequest.of(0, 5, Sort.Direction.DESC, "age");
        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(10);
        condition.setAgeLoe(30);
        condition.setUsername("member1");
        Page<Member> members = memberTestRepository.applyPagination(condition, pageRequest);
        assertEquals(5, members.getSize());
        assertEquals(1, members.getContent().size());

        assertEquals(1, members.getTotalElements());
        assertEquals(1, members.getTotalPages());
    }
}
