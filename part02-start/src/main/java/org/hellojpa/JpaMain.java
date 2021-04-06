package org.hellojpa;

import javax.persistence.*;
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
//            Member findMember = findMember(em);
//            saveMember(em, findMember);
            jpqlExec(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

//        실제 애플리케이션이 완전히 끝나면 팩토리를 닫아준다.
        entityManagerFactory.close();
    }

    private static Member updateMember(Member findMember) {
        findMember.setName("PS");
        return findMember;
    }
    private static Member findMember(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember = " + findMember);
        return findMember;
    }
    private static void saveMember(EntityManager em, Member findMember) {
        Object result = Objects.isNull(findMember) ? newMember() : updateMember(findMember);
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
        return em.createQuery("select m from Member as m where m.name = :name", Member.class)
                .setParameter("name", "PS");
    }

    private static TypedQuery<Member> findByIdWithGte(EntityManager em) {
        return em.createQuery("select m from Member as m where m.id <= :id", Member.class)
                .setParameter("id", 2);
    }

}
