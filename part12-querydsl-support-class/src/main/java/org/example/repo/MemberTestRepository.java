package org.example.repo;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import org.example.dto.MemberSearchCondition;
import org.example.entity.Member;
import org.example.repo.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.util.Objects.nonNull;
import static org.example.entity.QMember.member;
import static org.example.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author pilseong.ko
 */
@Repository
public class MemberTestRepository extends Querydsl4RepositorySupport<Member> {

    public MemberTestRepository() {
        super(Member.class);
    }

    public List<Member> basicSelect() {
        return select(member)
                .from(member)
                .fetch();
    }

    public List<Member> basicSelectFrom() {
        return selectFrom(member)
                .fetch();
    }


    public Page<Member> searchPageByApplyPage(MemberSearchCondition condition, Pageable pageable) {
        JPAQuery<Member> query = selectFrom(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                       teamNameEq(condition.getTeamName()),
                       ageGoe(condition.getAgeGoe()),
                       ageLoe(condition.getAgeLoe()));

        List<Member> content = getQuerydsl().applyPagination(pageable, query).fetch();
        return PageableExecutionUtils.getPage(content, pageable, query::fetchCount);
    }

    public Page<Member> applyPagination(MemberSearchCondition condition, Pageable pageable) {
        return super.applyPagination(pageable, query -> selectFrom(member)
                .leftJoin(member.team, team)
                .where(usernameEq(condition.getUsername()),
                       teamNameEq(condition.getTeamName()),
                       ageGoe(condition.getAgeGoe()),
                       ageLoe(condition.getAgeLoe())));
    }

    public Page<Member> applyPagination2(MemberSearchCondition condition, Pageable pageable) {
        return super.applyPagination(pageable,
                                     contentQuery ->
                                             contentQuery.selectFrom(member)
                                                         .leftJoin(member.team, team)
                                                         .where(usernameEq(condition.getUsername()),
                                                                teamNameEq(condition.getTeamName()),
                                                                ageGoe(condition.getAgeGoe()),
                                                                ageLoe(condition.getAgeLoe())),
                                     countQuery ->
                                             countQuery.select(member.id)
                                                       .from(member)
                                                       .leftJoin(member.team, team)
                                                       .where(usernameEq(condition.getUsername()),
                                                              teamNameEq(condition.getTeamName()),
                                                              ageGoe(condition.getAgeGoe()),
                                                              ageLoe(condition.getAgeLoe()))
                                    );
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
