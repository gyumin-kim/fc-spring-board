package example.springboard.util;

import example.springboard.dto.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public interface FileUtil {
    FileInfo handleFileStream(HttpServletRequest request, HttpSession session ,MultipartFile file);
    void downloadFileStream(HttpServletResponse response,Long boardId);
}
