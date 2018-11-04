package example.springboard.dto;

import java.util.Date;

public class FileInfo {
    private Long idx;
    private Long boardIdx;
    private String originalFileName;
    private String StoredFileName;
    private long fileSize;
    private Date regdate;
    private boolean delGb;

    public Long getIdx() {
        return idx;
    }

    public Long getBoardIdx() {
        return boardIdx;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public String getStoredFileName() {
        return StoredFileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public Date getRegdate() {
        return regdate;
    }

    public boolean isDelGb() {
        return delGb;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public void setBoardIdx(Long boardIdx) {
        this.boardIdx = boardIdx;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public void setStoredFileName(String storedFileName) {
        StoredFileName = storedFileName;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public void setDelGb(boolean delGb) {
        this.delGb = delGb;
    }

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
