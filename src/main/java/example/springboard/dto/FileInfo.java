package example.springboard.dto;

import java.util.Date;

public class FileInfo {
    private Long idx;
    private Long boardIdx;
    private String originFileName;
    private String StoredFileName;
    private long fileSize;
    private Date regdate;
    private boolean deldb;

    public Long getIdx() {
        return idx;
    }

    public Long getBoardIdx() {
        return boardIdx;
    }

    public String getOriginFileName() {
        return originFileName;
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

    public boolean isDeldb() {
        return deldb;
    }

    public void setIdx(Long idx) {
        this.idx = idx;
    }

    public void setBoardIdx(Long boardIdx) {
        this.boardIdx = boardIdx;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
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

    public void setDeldb(boolean deldb) {
        this.deldb = deldb;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "idx=" + idx +
                ", boardIdx=" + boardIdx +
                ", originFileName='" + originFileName + '\'' +
                ", StoredFileName='" + StoredFileName + '\'' +
                ", fileSize=" + fileSize +
                ", regdate=" + regdate +
                ", deldb=" + deldb +
                '}';
    }
}
