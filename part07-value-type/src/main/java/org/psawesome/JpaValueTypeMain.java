package org.psawesome;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.LongStream;

/**
 * @author pilseong.ko
 */
public class JpaValueTypeMain {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //            criteria(em);
            createMembers(500, em);

            //            exec(em.createQuery("select coalesce(m.name, '이름 없는 회원') from Member m", String.class));
            //            exec(em.createQuery("select nullif(m.name, '관리자') from Member m", String.class));
            exec(em.createQuery("select m, t from Member m left join Team t on m.name = t.name"));

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            entityManagerFactory.close();
        }
    }

    private static <T> void exec(TypedQuery<T> execQuery) {
        execQuery.setFirstResult(0)
                 .setMaxResults(30)
                 .getResultList()
                 .forEach(System.out::println);
    }

    private static void exec(Query execQuery) {
        execQuery.setFirstResult(0)
                 .setMaxResults(30)
                 .getResultList()
                 .forEach(System.out::println);
    }

    private static void criteria(EntityManager em) {
        //            사용할 준비
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Member> query = cb.createQuery(Member.class);

        // 루트 클래스 - 조회를 시작할 클래스
        Root<Member> m = query.from(Member.class);

        // 쿼리 생성
        CriteriaQuery<Member> cq = query.select(m)
                                        .where(cb.equal(m.get("name"), "kim"));
        List<Member> resultList = em.createQuery(cq)
                                    .getResultList();
    }

    private static void createMembers(int count, EntityManager em) {
        LongStream.iterate(0, l -> l + 1)
                  .mapToObj(id -> Member.builder()
                                        .age((int) id)
                                        .name(id % 3 == 0 ? "name num: " + id :
                                              id % 5 == 0 ? "관리자" : "회원")
                                        .homeAddress(new Address("citizen :" + id,
                                                                 "street : " + id,
                                                                 "450" + id))
                                        .workPeriod(new Period(LocalDateTime.now(),
                                                               LocalDateTime.now()))
                                        .build())
                  .limit(count)
                  .forEach(em::persist)
        ;
        LongStream.iterate(0, l -> l + 1)
                  .mapToObj(id -> Team.builder()
                                      .name("name num: " + id).build())
                  .limit(count)
                  .forEach(em::persist);

    }
}
