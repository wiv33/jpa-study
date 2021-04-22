package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * @author pilseong.ko
 */
public class Part06JpaMain {
    public static void main(String[] args) {
        //        애플리케이션 로딩 시점에 딱 하나만 만들어야 한다.
        EntityManagerFactory entityManagerFactory =
                Persistence.createEntityManagerFactory("hello");

        EntityManager em = entityManagerFactory.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Movie movie = new Movie();
            movie.setActor("aaa");
            movie.setDirector("bbbb");
            movie.setPrice(30);
            movie.setName("sdfasdfasdfasdf");
            em.persist(movie);

            em.flush();
            em.clear();

            Movie reference = em.getReference(Movie.class, 1L);
            System.out.println(entityManagerFactory.getPersistenceUnitUtil().isLoaded(reference));
            System.out.println(reference.getClass());

            //            System.out.println(reference.getName());
            //            Movie findMovie = em.find(Movie.class, 1L);
            //            System.out.println(findMovie.getClass());
            //            System.out.println(reference == findMovie);
            em.clear();
            System.out.println(reference.getName());
            //            String actor = findMovie.getActor();
            //            System.out.println("actor = " + actor);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
            entityManagerFactory.close();
        }

        //        실제 애플리케이션이 완전히 끝나면 팩토리를 닫아준다.
    }
}
