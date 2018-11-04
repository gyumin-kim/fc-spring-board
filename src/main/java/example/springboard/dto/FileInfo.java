package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

public class FileInfo {
    @Setter
    @Getter
    private Long idx;
    @Setter @Getter
    private Long boardIdx;
    @Setter @Getter
    private String originalFileName;
    @Setter @Getter
    private String StoredFileName;
    @Setter @Getter
    private long fileSize;
    @Setter @Getter
    private Date regdate;
    @Setter @Getter
    private boolean delGb;


    @Override
    public String toString() {
        return "FileInfo{" +
                "idx=" + idx +
                ", boardIdx=" + boardIdx +
                ", originalFileName='" + originalFileName + '\'' +
                ", StoredFileName='" + StoredFileName + '\'' +
                ", fileSize=" + fileSize +
                ", regdate=" + regdate +
                ", delGb=" + delGb +
                '}';
    }
}
