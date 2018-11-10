package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
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
    private String content;
    private String name;
    private FileInfo fileInfo;
}
