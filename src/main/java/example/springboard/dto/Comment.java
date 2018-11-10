package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Setter
@Getter
@ToString
public class Comment {
    private Long id;
    private Long boardId;
    private Long parentCommentId;
    private int seq;
    private Long memberId;
    private String name;
    private String content;
    private String ipAddr;
    private Date regDate;
    private int childCommentCount;
    private int isDeleted;
}
