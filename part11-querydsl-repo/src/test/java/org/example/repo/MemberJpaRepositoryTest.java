package org.example.repo;

import org.example.dto.MemberSearchCondition;
import org.example.entity.Member;
import org.example.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pilseong.ko
 */
@ActiveProfiles("test")
@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    void basicTest() {
        var member = new Member("member1", 30);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findId(1L).get();
        assertEquals(member, findMember);

        List<Member> memberList = memberJpaRepository.findAll();
        assertThat(memberList).containsExactly(member);

        List<Member> memberList2 = memberJpaRepository.findByUsername("member1");
        assertThat(memberList2).containsExactly(member);

    }

    @Test
    void basicQuerydslTest() {
        var member = new Member("member1", 30);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findId(1L).get();
        assertEquals(member, findMember);

        List<Member> memberList = memberJpaRepository.findAll_querydsl();
        assertThat(memberList).containsExactly(member);

        List<Member> memberList2 = memberJpaRepository.findByUsername_querydsl("member1");
        assertThat(memberList2).containsExactly(member);

    }

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
    void testSearch() {
        var condition = new MemberSearchCondition();
        condition.setAgeLoe(40);
        condition.setAgeGoe(35);
        condition.setTeamName("teamB");

        var result = memberJpaRepository.search(condition);

        assertEquals(1, result.size());
        assertEquals("teamB", result.get(0).getTeamName());
        assertEquals("member4", result.get(0).getUsername());
    }


}
