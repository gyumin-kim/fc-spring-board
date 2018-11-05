package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class Comment {
    @Setter @Getter
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
    @Setter @Getter
    private String content;
    @Setter @Getter
    private String ipAddr;
    @Setter @Getter
    private Date regdate;

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
