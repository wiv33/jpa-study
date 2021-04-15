package org.psawesome;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.concurrent.locks.Lock;

/**
 * @author pilseong.ko
 */
public class JpaMain {
    public static void main(String[] args) {
        //        애플리케이션 로딩 시점에 딱 하나만 만들어야 한다.
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Member member = new Member();
            member.setName("MemberName");
            em.persist(member);

            Locker locker = new Locker();
            locker.setName("locker!");
            locker.setMember(member);
            member.setLocker(locker);

            em.persist(locker);

            em.flush();
            em.clear();

            Member member1 = em.find(Member.class, 1L);
            System.out.println("==============================");

            System.out.println("member.getLocker null check : " + (member1.getLocker() == null));
            String name = member1.getLocker()
                               .getName();
            System.out.println("name = " + name);


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
}
