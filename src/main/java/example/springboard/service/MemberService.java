package example.springboard.service;

import example.springboard.dto.Member;

public interface MemberService {
    Member addMember(Member member);
    String findNameById(Long MemberId);
    String findPasswordByName(String memberName);
    Member findMemberById(Long memberId);
//    Member findMemberByName(String memberName);
    int updateMemberInfo(Member member);
}
