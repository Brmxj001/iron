package iron.controller;

import com.google.gson.Gson;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import iron.bean.home;
import iron.bean.homeimg;
import iron.dao.HomeDAO;
import iron.dao.HomeImgDAO;
import iron.util.Qiniu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@Slf4j
@RestController
public class HomeController {
    @Autowired
    HomeImgDAO homeImgDAO;
    @Autowired
    HomeDAO homeDAO;

    @PostMapping("/back/home/img/add")
    public String doHomeImgAdd(home h, MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        home home = homeDAO.findById(h.getId()).get();
        home.setContent(key);
        homeDAO.save(home);
        Response res = Qiniu.getQiniu().getUploadManager().put(file.getBytes(), key, Qiniu.getQiniu().getUpToken());
        return new Gson().fromJson(res.bodyString(), DefaultPutRet.class).key;
    }

    @PostMapping("/back/homeimg/get")
    public List<homeimg> doHomeImgGet() {
        return homeImgDAO.findAll();
    }

    @PostMapping("/back/homeimg/delete")
    public void doHomeImgDelete(Integer id) {
        homeImgDAO.deleteById(id);
    }

    @PostMapping("/back/home/edit")
    public void HomeEdit(home h) {
        homeDAO.save(h);
    }

    @PostMapping("/back/home/Carousel/delete")
    public void deleteHomeCarousel(Integer id) {
        homeImgDAO.deleteById(id);
    }

    @PostMapping("/back/home/Carousel/get")
    public List<homeimg> getHomeCarousel(){
        log.info("get");
        return homeImgDAO.findAll();
    }

    @PostMapping("/back/home/Carousel/add")
    public void addHomeCarousel(MultipartFile file) throws IOException {
        String key = UUID.randomUUID().toString();
        Qiniu.getQiniu().getUploadManager().put(file.getBytes(), key, Qiniu.getQiniu().getUpToken());
        homeimg img = new homeimg();
        img.setPath(key);
        homeImgDAO.save(img);
    }
}
