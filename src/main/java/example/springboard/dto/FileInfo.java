package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
public class FileInfo {
    @Setter
    @Getter
    private Long idx;
    @Setter @Getter
    private Long boardIdx;
    @Setter @Getter
    private String originalFileName;
    @Setter @Getter
    private String storedFileName;
    @Setter @Getter
    private long fileSize;
    @Setter @Getter
    private Date regdate;
    @Setter @Getter
    private boolean delGb;
    @Setter @Getter
    private String uuid;
    @Getter @Setter
    private String contentType;
    @Override
    public String toString() {
        return "FileInfo{" +
                "idx=" + idx +
                ", boardIdx=" + boardIdx +
                ", originalFileName='" + originalFileName + '\'' +
                ", storedFileName='" + storedFileName + '\'' +
                ", fileSize=" + fileSize +
                ", regdate=" + regdate +
                ", delGb=" + delGb +
                '}';
    }
}
