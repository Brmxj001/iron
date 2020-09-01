package iron.util;


import iron.dao.HomeDAO;
import iron.util.Qiniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@Service("IronUtil")
public class IronUtil {

    @Autowired
    HomeDAO homeDAO;
    public String uploadImg(MultipartFile file, String key) throws IOException {
        String result = key + Calendar.getInstance().getTime()+UUID.randomUUID().toString();
        Qiniu.getQiniu().getUploadManager().put(file.getBytes(), result, Qiniu.getQiniu().getUpToken());
        return result;
    }
    public String getUUID(){
        return UUID.randomUUID().toString();
    }
    public String getUUID(String...args){
        String result = "";
        for (String s:args) {
            result += s+"__";
        }
        return result+UUID.randomUUID().toString();
    }

}
