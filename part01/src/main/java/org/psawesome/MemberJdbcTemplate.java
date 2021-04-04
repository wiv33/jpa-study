package org.psawesome;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class MemberJdbcTemplate {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertActor;

    public Member findOne(Long id) {
        String sql = "SELECT MEMBER_ID, USERNAME, PHONE_NUMBER FROM MEMBER WHERE MEMBER_ID = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(), id);
    }

    public Long save(final Member member) {
        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("USERNAME", member.getUsername())
                .addValue("PHONE_NUMBER", member.getPhoneNumber());

        Number newId = insertActor.executeAndReturnKey(parameters);
        return newId.longValue();
    }
}
