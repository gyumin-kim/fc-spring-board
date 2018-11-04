package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;

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
                ", name='" + name + '\'' +
                ", board_body_content='" + content + '\'' +
                '}';
    }
}
