package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

@ToString
public class Board {
    @Setter @Getter
    private Long id;
    @Setter @Getter
    private Long originId;
    @Setter @Getter
    private int depth;
    @Setter @Getter
    private int replySeq;
    @Setter @Getter
    private Long categoryId;
    @Setter @Getter
    private Long memberId;
    @Setter @Getter
    private String title;
    @Setter @Getter
    private Date regDate;
    @Setter @Getter
    private String ipAddr;
    @Setter @Getter
    private String content;
    @Setter @Getter
    private String name;
    @Setter @Getter
    private FileInfo fileInfo;
}
