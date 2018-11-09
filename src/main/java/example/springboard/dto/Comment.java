package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
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
    private Date regDate;
}
