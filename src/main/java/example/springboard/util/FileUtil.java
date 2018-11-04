package example.springboard.util;

import example.springboard.dto.FileInfo;
import org.springframework.web.multipart.MultipartFile;

public interface FileUtil {
    public FileInfo handleFileStream(MultipartFile file);
}
