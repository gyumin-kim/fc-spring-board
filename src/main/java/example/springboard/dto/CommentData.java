package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentData {
    private String content;
    private Long memberId;
    private Long parentCommentId;
}
