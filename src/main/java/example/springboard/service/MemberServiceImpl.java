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

    /**
     * 회원가입
     */
    @Transactional
    @Override
    public Member addMember(Member member) {
        Long id = memberDao.insertMember(member);
        member.setId(id);
        return member;
    }

    /**
     * 게시글 조회(제목, 내용, 제목+내용) 시 작성자 이름(name)을 가져오기 위한 메소드
     */
    // 작성자 이름 가져오는 메소드 -> board 테이블에 다 조인으로 해놔서 필요가 있나...?
    @Transactional(readOnly = true)
    @Override
    public String findNameById(Long MemberId) {
        return memberDao.selectNameById(MemberId);
    }

    /**
     * 로그인 화면에서 사용자가 입력한 name을 바탕으로 password를 가져옴
     * 가져온 password를 사용자가 입력한 password와 service에서 비교
     */
    @Transactional(readOnly = true)
    @Override
    public String findPasswordByName(String memberName) {
        return memberDao.selectPasswordByName(memberName);
    }

    /**
     * 회원정보 보기 및 수정 용도
     */
    @Transactional(readOnly = true)
    @Override
    public Member findMemberByEmail(String email) {
        return memberDao.selectMemberByEmail(email);
    }

//    @Transactional(readOnly = true)
//    @Override
//    public Member findMemberByName(String memberName) {
//        return memberDao.selectMemberByName(memberName);
//    }

    /**
     * 회원정보 수정
     */
    @Transactional
    @Override
    public int updateMemberInfo(Member member) {
        return memberDao.updateMember(member);
    }
}
