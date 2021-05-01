package org.example.controller;

import org.example.entity.Member;
import org.example.entity.Team;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.LongStream;

/**
 * @author pilseong.ko
 */
@Profile("local")
@Component
public class InitMember implements CommandLineRunner {

    private final InitMemberService initMemberService;

    @PersistenceContext
    EntityManager em;

    public InitMember(InitMemberService initMemberService) {
        this.initMemberService = initMemberService;
        System.out.println("InitMember.InitMember.constructor");
    }

    @Transactional
    public void init() {
        System.out.println("InitMember.init()");
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("CommandLineRunner.run()");
        var teamA = new Team("teamA");
        var teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        LongStream.range(0, 100)
                  .forEach(l -> {
                      var selectedTeam = l % 2 == 0 ? teamA : teamB;
                      em.persist(new Member("member_" + l, ((int) l), selectedTeam));
                  });

    }

    @Component
    static class InitMemberService {
        public InitMemberService() {
            System.out.println("InitMemberService.InitMemberService.constructor");
        }

        @PersistenceContext
        EntityManager em;

        @Transactional
        public void init() {
            System.out.println("InitMemberService.init()");
            var teamA = new Team("teamA");
            var teamB = new Team("teamB");
            em.persist(teamA);
            em.persist(teamB);

            LongStream.range(0, 100)
                      .forEach(l -> {
                          var selectedTeam = l % 2 == 0 ? teamA : teamB;
                          em.persist(new Member("member_" + l, ((int) l), selectedTeam));
                      });

        }
    }
}
