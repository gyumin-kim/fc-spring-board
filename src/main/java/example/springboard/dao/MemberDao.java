package example.springboard.dao;

import example.springboard.dto.Member;

public interface MemberDao {
    int insertMember(Member member);
    String selectNameById(Long id);
    String selectPasswordByName(String name);
    Member selectMemberByName(String name);
}
