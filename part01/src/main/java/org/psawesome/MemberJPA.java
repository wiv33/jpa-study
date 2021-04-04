package org.psawesome;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MemberJPA {

    @PersistenceContext
    EntityManager jpa;

    public void save(Member member) {
        jpa.persist(member);
    }

    public Member findOne(Long memberId) {
        return jpa.find(Member.class, memberId);
    }

}
