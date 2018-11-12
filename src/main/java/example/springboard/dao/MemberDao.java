package example.springboard.dao;

import example.springboard.dto.Member;

import java.util.List;

public interface MemberDao {
    Long insertMember(Member member);
    String selectNameById(Long id);
    String selectPasswordByName(String name);
    Member selectMemberByEmail(String email);
    Member selectMemberById(Long id);
//    Member selectMemberByName(String name);
    int updateMember(Member member);
    List<Member> selectAllMember();
    int updateMemberPermission(Member member, int[] permissions);
}
