package org.example.repo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.example.dto.MemberSearchCondition;
import org.example.dto.MemberTeamDto;
import org.example.dto.QMemberTeamDto;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.entity.QMember.member;
import static org.example.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author pilseong.ko
 */
public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<MemberTeamDto> search(MemberSearchCondition condition) {
        System.out.println("MemberRepositoryCustomImpl.search");
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
