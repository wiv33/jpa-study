package org.example.dto;

import lombok.Data;

/**
 * @author pilseong.ko
 */
@Data
public class MemberSearchCondition {

    private String username;

    private String teamName;

    private Integer ageGoe;

    private Integer ageLoe;
}
