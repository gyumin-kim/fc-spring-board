package example.springboard.util;

import example.springboard.dto.FileInfo;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class FileUtilImpl implements FileUtil{

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String dateStr = simpleDateFormat.format(new Date());

    String baseDir = "C:\\spring";
    String saveDir = baseDir + "\\" + dateStr;
    String saveFile = saveDir + "/" + UUIDGenerator.getRandomString();

    public FileInfo handleFileStream(MultipartFile file){
        System.out.println("------file info start ----");
        System.out.println(file.getContentType());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getName());
        System.out.println(file.getSize());

        String uuidStr = UUIDGenerator.getRandomString();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String dateStr = simpleDateFormat.format(new Date());

        String baseDir = "C:\\spring";
        String saveDir = baseDir + "\\" + dateStr;
        String saveFile = saveDir + "/" + uuidStr;

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
            System.out.println("파일 업로드중 오류가 발생했습니다.");
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
        System.out.println("------file info end ----");

        FileInfo fileInfo = new FileInfo();
        fileInfo.setOriginalFileName(file.getOriginalFilename());
        fileInfo.setStoredFileName(saveFile);
        fileInfo.setFileSize(file.getSize());
        return fileInfo;
    }
}
