package org.example.repo;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.dto.MemberSearchCondition;
import org.example.dto.MemberTeamDto;
import org.example.dto.QMemberTeamDto;
import org.example.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.example.entity.QMember.member;
import static org.example.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author pilseong.ko
 */
@Repository
public class MemberJpaRepository {

    private final EntityManager em;

    private final JPAQueryFactory queryFactory;

    public MemberJpaRepository(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    public void save(Member member) {
        em.persist(member);
    }

    public Optional<Member> findId(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                 .getResultList();
    }

    public List<Member> findAll_querydsl() {
        return queryFactory.selectFrom(member)
                           .fetch();
    }

    public List<Member> findByUsername(String username) {
        return em.createQuery("select m from Member m where m.username = :username", Member.class)
                 .setParameter("username", username)
                 .getResultList();
    }

    public List<Member> findByUsername_querydsl(String username) {
        return queryFactory.selectFrom(member)
                           .where(member.username.eq(username))
                           .fetch();
    }

    public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {

        BooleanBuilder builder = new BooleanBuilder();
        if (hasText(condition.getUsername())) {
            builder.and(member.username.eq(condition.getUsername()));
        }
        if (hasText(condition.getTeamName())) {
            builder.and(team.name.eq(condition.getTeamName()));
        }
        if (condition.getAgeGoe() != null) {
            builder.and(member.age.goe(condition.getAgeGoe()));
        }
        if (condition.getAgeLoe() != null) {
            builder.and(member.age.loe(condition.getAgeLoe()));
        }
        return queryFactory.select(new QMemberTeamDto(member.id.as("memberId"),
                                                      member.username,
                                                      member.age,
                                                      team.id.as("teamId"),
                                                      team.name.as("teamName")))
                           .from(member)
                           .leftJoin(member.team, team)
                           .where(builder)
                           .fetch();
    }


    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        return queryFactory.select(new QMemberTeamDto(member.id.as("memberId"),
                                                      member.username,
                                                      member.age,
                                                      team.id.as("teamId"),
                                                      team.name.as("teamName")))
                           .from(member)
                           .leftJoin(member.team, team)
                           .where(usernameEq(condition.getUsername()),
                                  teamNameEq(condition.getTeamName()),
                                  ageGoe(condition.getAgeGoe()),
                                  ageLoe(condition.getAgeLoe()))
                           .fetch();
    }

    private BooleanExpression usernameEq(String username) {
        return hasText(username) ? member.username.eq(username) : null;
    }

    private BooleanExpression teamNameEq(String teamName) {
        return hasText(teamName) ? team.name.eq(teamName) : null;
    }

    private BooleanExpression ageGoe(Integer ageGoe) {
        return nonNull(ageGoe) ? member.age.goe(ageGoe) : null;
    }

    private BooleanExpression ageLoe(Integer ageLoe) {
        return nonNull(ageLoe) ? member.age.loe(ageLoe) : null;
    }
}
