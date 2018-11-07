package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class CommentData {
    @Getter @Setter
    private String content;
    @Getter @Setter
    private Long memberId;
}
