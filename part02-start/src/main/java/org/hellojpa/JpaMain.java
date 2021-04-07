package org.hellojpa;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class JpaMain {
    public static void main(String[] args) {
//        애플리케이션 로딩 시점에 딱 하나만 만들어야 한다.
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");

        /*
        1. db connection
        2. transaction
        3. save
        4. terminal
        */
        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            System.out.println("=== before ===");
            Member findMember = findMember(em);
            saveMember(em, findMember);
            System.out.println("=== after ===");
            jpqlExec(em);

            Member res = findMember(em);
            System.out.println("res = " + res);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            tx.rollback();
        } finally {
            em.close();
            entityManagerFactory.close();
        }

//        실제 애플리케이션이 완전히 끝나면 팩토리를 닫아준다.
    }

    private static Member updateMember(Member findMember) {
        findMember.setName("PS");
        return findMember;
    }
    private static Member findMember(EntityManager em) {
        return em.find(Member.class, 1L);
    }
    private static void saveMember(EntityManager em, Member findMember) {
        Member result = Objects.isNull(findMember) ? newMember() : updateMember(findMember);
        em.persist(result);
    }

    private static Member newMember() {
        Member member = new Member();
        member.setId(1L);
        member.setName("Hello Member");
        return member;
    }

    private static List<Member> jpqlExec(EntityManager em) {
//        멤버 객체를 대상으로 쿼리를 작성한다.
        List<Member> result = findByAge(em)
                .getResultList();

        findByNames(em)
                .getResultStream()
                .forEach(System.out::println);

        Member member1 = em.find(Member.class, 1L);
        System.out.println("member1 = " + member1);
        findByNames(em)
                .getResultStream()
                .forEach(System.out::println);

        for (Member member : result) {
            System.out.println("member name = " + member.getName());
        }

        return result;
    }

    private static TypedQuery<Member> findByAge(EntityManager em) {
        return em.createQuery("select m from Member as m where m.age = :age", Member.class)
                .setParameter("age", 18);
    }

    private static TypedQuery<Member> findByNames(EntityManager em) {
        return em.createQuery("select m from Member as m where m.name = :name and m.id = :id", Member.class)
                .setParameter("name", "PS")
                .setParameter("id", 1L)
                ;
    }

    private static TypedQuery<Member> findByIdWithGte(EntityManager em) {
        return em.createQuery("select m from Member as m where m.id = :id", Member.class)
                .setParameter("id", 1L);
    }

}


//=== before ===
//Hibernate:
//    select
//        member0_.id as id1_0_0_,
//        member0_.age as age2_0_0_,
//        member0_.name as name3_0_0_
//    from
//        Member member0_
//    where
//        member0_.id=?
//findMember = Member{id=1, name='PS'}
//=== after ===
//Hibernate:
//    /* select
//        m
//    from
//        Member as m
//    where
//        m.age = :age */ select
//            member0_.id as id1_0_,
//            member0_.age as age2_0_,
//            member0_.name as name3_0_
//        from
//            Member member0_
//        where
//            member0_.age=?
//Hibernate:
//    /* select
//        m
//    from
//        Member as m
//    where
//        m.name = :name
//        and m.id = :id */ select
//            member0_.id as id1_0_,
//            member0_.age as age2_0_,
//            member0_.name as name3_0_
//        from
//            Member member0_
//        where
//            member0_.name=?
//            and member0_.id=?
//Member{id=1, name='PS'}
//member1 = Member{id=1, name='PS'}
//Hibernate:
//    /* select
//        m
//    from
//        Member as m
//    where
//        m.name = :name
//        and m.id = :id */ select
//            member0_.id as id1_0_,
//            member0_.age as age2_0_,
//            member0_.name as name3_0_
//        from
//            Member member0_
//        where
//            member0_.name=?
//            and member0_.id=?
//Member{id=1, name='PS'}
//findMember = Member{id=1, name='PS'}
//res = Member{id=1, name='PS'}