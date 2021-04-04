package org.psawesome;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Member {

//    @Column("MEMBER_ID")
    private final Long memberId;
    private final String username;
//    @Column("PHONE_NUMBER")
    private final String phoneNumber;
}
