package example.springboard.dto;

import java.util.Date;

public class Comment {
    private Long id;
    private Long boardId;
    private Long parentCommentId;
    private int seq;
    private Long memberId;
    private String content;
    private String ipAddr;
    private Date regdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentCommentId(Long parentCommentId) {
        this.parentCommentId = parentCommentId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", parentCommentId=" + parentCommentId +
                ", seq=" + seq +
                ", memberId=" + memberId +
                ", content='" + content + '\'' +
                ", ipAddr='" + ipAddr + '\'' +
                ", Regdate='" + regdate + '\'' +
                '}';
    }
}
