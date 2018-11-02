package example.springboard.service;

import example.springboard.dto.Member;

public interface MemberService {
    Member addMember(Member member);
    String findNameById(Long MemberId);
    String findPasswordByName(String memberName);
    Member findMemberByEmail(String email);
//    Member findMemberByName(String memberName);
    int updateMemberInfo(Member member);
}
