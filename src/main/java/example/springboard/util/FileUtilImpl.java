package example.springboard.util;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileUtil {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
    String dateStr = simpleDateFormat.format(new Date());

    String baseDir = "C:\\spring";
    String saveDir = baseDir + "\\" + dateStr;
    String saveFile = saveDir + "/" + Util.getRandomString();

    public List<Map<String,Object>> parseInsertFileInfo(Map<String,Object> map,@RequestParam("file") MultipartFile file ){
        Iterator<String> iterator = file.getName();
    }
}
