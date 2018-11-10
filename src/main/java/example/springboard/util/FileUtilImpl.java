package example.springboard.util;

import example.springboard.dao.FileDownloadDaoImpl;
import example.springboard.dto.FileInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtilImpl implements FileUtil{
    private static final Log log = LogFactory.getLog(FileUtilImpl.class);
    private FileDownloadDaoImpl fileDownloadDao;

    public FileUtilImpl(FileDownloadDaoImpl fileDownloadDao) {
        this.fileDownloadDao = fileDownloadDao;
    }

    public FileInfo handleFileStream(HttpServletRequest request, HttpSession session,MultipartFile file ){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = simpleDateFormat.format(new Date());
        String baseDir = request.getSession().getServletContext().getRealPath("/");//현재경로로 파일을 업로드
        String saveDir = baseDir + "\\" + dateStr;
        String saveFile = saveDir + "/" + UUIDGenerator.getRandomString();

        File file1 = new File(saveDir);
        file1.mkdirs();

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try{
            inputStream = file.getInputStream();
            outputStream = new FileOutputStream(saveFile);
            byte[] buffer = new byte[1024];
            int readCount = 0;
            while((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,readCount);
            }
        }catch (Exception ex){
            log.info("파일 업로드중 오류가 발생했습니다.");
        }finally {
            if(inputStream != null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    outputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        FileInfo fileInfo = new FileInfo();
        fileInfo.setOriginalFileName(file.getOriginalFilename());
        fileInfo.setStoredFileName(saveFile);
        fileInfo.setFileSize(file.getSize());
        fileInfo.setContentType(file.getContentType());
        return fileInfo;
    }

    @Override
    public void downloadFileStream(HttpServletResponse response, Long boardId) {

        FileInfo fileInfo = fileDownloadDao.download(boardId);
        String contentType = fileInfo.getContentType();
        long size = fileInfo.getFileSize();
        String originalFileName = fileInfo.getOriginalFileName();
//        String savePath = "/tmp/2018/10/31/90b51b95-4d5a-4cb0-829e-c29947a9dab5";
        response.setContentLength((int)size);
        response.setContentType("application/x-msdownload");
        response.setContentType(contentType);
        try {
            originalFileName = URLDecoder.decode(originalFileName, "ISO8859_1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment; filename="+ originalFileName);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = new FileInputStream(fileInfo.getStoredFileName());
            outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int readCount = 0;
            while ((readCount = inputStream.read(buffer)) != -1){
                outputStream.write(buffer,0,readCount);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }
            if (outputStream != null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}