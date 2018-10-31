package example.springboard.service;

import example.springboard.dao.MemberDao;
import example.springboard.dto.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {
    private MemberDao memberDao;

    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Transactional
    @Override
    public Member addMember(Member member) {
        Long id = memberDao.insertMember(member);
        member.setId(id);
        return member;
    }

    @Transactional(readOnly = true)
    @Override
    public String findNameById(Long MemberId) {
        return memberDao.selectNameById(MemberId);
    }

    @Transactional(readOnly = true)
    @Override
    public String findPasswordByName(String memberName) {
        return memberDao.selectPasswordByName(memberName);
    }

    @Transactional(readOnly = true)
    @Override
    public Member findMemberById(Long memberId) {
        return memberDao.selectMemberById(memberId);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Member findMemberByName(String memberName) {
//        return memberDao.selectMemberByName(memberName);
//    }

    @Transactional
    @Override
    public int updateMemberInfo(Member member) {
        return memberDao.updateMember(member);
    }
}
