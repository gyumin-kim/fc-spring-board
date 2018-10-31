package example.springboard.dao;

import example.springboard.dto.Member;

public interface MemberDao {
    Long insertMember(Member member);
    String selectNameById(Long id);
    String selectPasswordByName(String name);
    Member selectMemberById(Long id);
//    Member selectMemberByName(String name);
    int updateMember(Member member);
}
