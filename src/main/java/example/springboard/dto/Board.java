package example.springboard.dto;

import java.util.Date;
import java.util.List;

public class Board {
    private Long id;
    private Long originId;
    private int depth;
    private int replySeq;
    private Long categoryId;
    private Long memberId;
    private String title;
    private Date regDate;
    private String ipAddr;
    private BoardBody boardBody;
    private Member member;

    public BoardBody getBoardBody() {
        return boardBody;
    }

    public void setBoardBody(BoardBody boardBody) {
        this.boardBody = boardBody;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOriginId() {
        return originId;
    }

    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getReplySeq() {
        return replySeq;
    }

    public void setReplySeq(int replySeq) {
        this.replySeq = replySeq;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", originId=" + originId +
                ", depth=" + depth +
                ", replySeq=" + replySeq +
                ", categoryId=" + categoryId +
                ", memberId=" + memberId +
                ", title='" + title + '\'' +
                ", regDate=" + regDate +
                ", ipAddr='" + ipAddr + '\'' +
                '}';
    }
}
