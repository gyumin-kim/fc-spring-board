package example.springboard.dao;

import example.springboard.dto.FileInfo;

public interface FileDownloadDao {
    FileInfo download(Long boardIdx);
}
