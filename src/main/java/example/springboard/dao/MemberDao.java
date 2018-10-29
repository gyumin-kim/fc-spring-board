package example.springboard.dao;

import example.springboard.dto.Member;

public interface MemberDao {
    Long addMember(Member member);
    String getName(Long id);
    String getPassword(String name);
    Member getMember(String name);
}
