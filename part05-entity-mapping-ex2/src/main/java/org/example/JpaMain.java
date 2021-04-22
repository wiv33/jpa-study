package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
            Member savedMember = new Member();
            savedMember.setCity("sdfsdf");
            savedMember.setName("psdf");

            Order savedOrder = new Order();
            savedOrder.setMember(savedMember);

            em.persist(savedMember);
            em.persist(savedOrder);

            em.flush();
            em.clear();

            //             Order order = em.find(Order.class, 2L);
            em.createQuery("select o from Order as o")
              .getResultList().forEach(System.out::println);

            System.out.println("-===============================");
            //            Member memberId1 = order.getMember();
            //            System.out.println(memberId1.getCity());

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
