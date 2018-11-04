package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Comment {
    @Setter
    @Getter
    private Long id;
    @Setter @Getter
    private Long boardId;
    @Setter @Getter
    private Long parentCommentId;
    @Setter @Getter
    private int seq;
    @Setter @Getter
    private Long memberId;
    @Setter @Getter
    private String name;
    private String content;
    @Setter @Getter
    private String ipAddr;
    @Setter @Getter
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
