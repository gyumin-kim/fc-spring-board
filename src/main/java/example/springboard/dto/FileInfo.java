package example.springboard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class FileInfo {
    private Long idx;
    private Long boardIdx;
    private String originalFileName;
    private String storedFileName;
    private long fileSize;
    private Date regDate;
    private boolean delGb;
    private String uuid;
    private String contentType;
}
