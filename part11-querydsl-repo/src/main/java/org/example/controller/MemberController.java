package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.MemberSearchCondition;
import org.example.dto.MemberTeamDto;
import org.example.repo.MemberJpaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author pilseong.ko
 */
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberJpaRepository memberJpaRepository;

    @GetMapping("/v1/members")
    public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
        return memberJpaRepository.search(condition);
    }
}
