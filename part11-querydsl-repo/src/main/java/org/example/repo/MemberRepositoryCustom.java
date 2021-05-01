package org.example.repo;

import org.example.dto.MemberSearchCondition;
import org.example.dto.MemberTeamDto;

import java.util.List;

/**
 * @author pilseong.ko
 */
public interface MemberRepositoryCustom {
    List<MemberTeamDto> search(MemberSearchCondition condition);
}
