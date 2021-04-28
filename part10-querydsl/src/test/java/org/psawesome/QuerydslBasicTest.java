package org.psawesome;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.psawesome.dto.MemberDto;
import org.psawesome.dto.QMemberDto;
import org.psawesome.dto.UserDto;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author pilseong.ko
 */
@SpringBootTest
@Transactional
@Commit
public class QuerydslBasicTest {

    @PersistenceContext
    EntityManager em;

    JPAQueryFactory queryFactory;

    QMember member = QMember.member;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);
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
    void testProjection() {

        List<String> fetch = queryFactory.select(member.username)
                                         .from(member)
                                         .fetch();

        List<Tuple> tuples = queryFactory
                .select(member.username, member.age)
                .from(member)
                .fetch();

        for (Tuple tuple : tuples) {
            String name = tuple.get(member.username);
            Integer age = tuple.get(member.age);
            System.out.println(name + " : " + age);
        }
    }

    @Test
    void findDtoByJPQL() {
        List<MemberDto> resultList = em.createQuery("select new org.psawesome.dto.MemberDto(m.username, m.age) from Member m", MemberDto.class)
                                       .getResultList();

        resultList.forEach(System.out::println);
    }


    @Test
    void testFindDtoByQuerydsl() {
        List<MemberDto> fetch = queryFactory
                .select(Projections.bean(MemberDto.class, member.username, member.age))
                .from(member)
                .fetch();

        System.out.println(fetch.size());

    }

    @Test
    void testDtoByConstructor() {
        List<MemberDto> fetch = queryFactory.select(Projections.constructor(MemberDto.class, member.username, member.age))
                                            .from(member)
                                            .fetch();

        for (MemberDto m : fetch) {
            System.out.println(m.getUsername() + " : " + m.getAge());
        }
    }

    @Test
    void testDtoByField() {
        List<MemberDto> fetch = queryFactory
                .select(Projections.fields(MemberDto.class,
                                           member.username,
                                           member.age))
                .from(member)
                .fetch();

        for (MemberDto m : fetch) {
            System.out.println(m.getUsername() + " : " + m.getAge());
        }
    }

    @Test
    void testUserDto() {
        List<UserDto> fetch = queryFactory
                .select(Projections.fields(UserDto.class,
                                           member.username,
                                           member.age))
                .from(member)
                .fetch();

        for (UserDto m : fetch) {
            System.out.println(m.getName() + " : " + m.getAge());
        }
    }

    @Test
    void name() {
        QMember memberSub = new QMember("memberSub");
        List<UserDto> fetch = queryFactory
                .select(Projections.fields(
                        UserDto.class,
                        member.username.as("name"),
                        ExpressionUtils.as(
                                JPAExpressions.select(memberSub.age.max())
                                              .from(memberSub), "age")))
                .from(member)
                .fetch();

        for (UserDto m : fetch) {
            System.out.println(m.getName() + " : " + m.getAge());
        }
    }

    @Test
    void testFindDtoByQueryProjection() {
        queryFactory
                .select(new QMemberDto(member.username, member.age))
                .from(member)
                .fetch()
                .forEach(System.out::println);
    }

    @Test
    void testDynamicQueryBooleanBuilder() {
        String usernameParam = "member1";
        Integer ageParam = 10;

        //        List<Member> members = searchMembers(usernameParam, ageParam);
        List<Member> members = searchMembers2(usernameParam, ageParam);
        assertEquals(1, members.size());
    }

    private List<Member> searchMembers(String usernameCond, Integer ageCond) {
        BooleanBuilder builder = new BooleanBuilder();
        if (usernameCond != null) {
            builder.and(member.username.eq(usernameCond));
        }
        if (ageCond != null) {
            builder.and(member.age.eq(ageCond));
        }
        return queryFactory
                .selectFrom(member)
                .where(builder)
                .fetch();
    }


    private List<Member> searchMembers2(String usernameCond, Integer ageCond) {
        return queryFactory
                .selectFrom(member)
                //                .where(usernameEq(usernameCond), ageEq(ageCond))
                .where(allEq(usernameCond, ageCond))
                .fetch();
    }

    private BooleanExpression allEq(String usernameCond, Integer ageCond) {
        return this.usernameEq(usernameCond).and(ageEq(ageCond));
    }

    private Predicate ageEq(Integer ageCond) {
        return ageCond != null ? member.age.eq(ageCond) : null;
    }

    private BooleanExpression usernameEq(String usernameCond) {
        return usernameCond != null ?
               member.username.eq(usernameCond) : null;
    }


    @Test
    void testBulkUpdate() {
        long count = queryFactory
                .update(member)
                .set(member.age, (member.age.add(1)))
                .where(member.age.lt(28))
                .execute();
        System.out.println("count = " + count);

        em.flush();
        em.clear();

        queryFactory.selectFrom(member)
                    .fetch()
                    .forEach(System.out::println);
    }

    @Test
    void testBulkDelete() {
        long count = queryFactory
                .delete(member)
                .where(member.age.lt(28))
                .execute();

        System.out.println("count = " + count);
        em.flush();
        em.clear();

        queryFactory
                .selectFrom(member)
                .fetch()
                .forEach(System.out::println);
    }

    @Test
    void testSqlFunction() {
        String s = queryFactory
                .select(Expressions.stringTemplate("function('replace', {0}, {1}, {2})",
                                                   member.username, "member", "M"))
                .from(member)
                .fetchFirst();
        System.out.println("s = " + s);
    }
}
