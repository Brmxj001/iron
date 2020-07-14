package iron.service.impl;


import iron.dao.HomeDAO;
import iron.util.Qiniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class IronUtil {

    @Autowired
    HomeDAO homeDAO;
    public void uploadImg(MultipartFile file, String key) throws IOException {
        Qiniu.getQiniu().getUploadManager().put(file.getBytes(), key, Qiniu.getQiniu().getUpToken());
    }
    public void doSetHome(Model m) {
        m.addAttribute("title", homeDAO.findByType("title"));
        m.addAttribute("titlee", homeDAO.findByType("titlee"));
        m.addAttribute("sub1", homeDAO.findByType("sub1"));
        m.addAttribute("sub1e", homeDAO.findByType("sub1e"));
        m.addAttribute("sub2", homeDAO.findByType("sub2"));
        m.addAttribute("sub2e", homeDAO.findByType("sub2e"));
        m.addAttribute("sub3", homeDAO.findByType("sub3"));
        m.addAttribute("sub3e", homeDAO.findByType("sub3e"));
        m.addAttribute("sub1text", homeDAO.findByType("sub1text"));
        m.addAttribute("sub1texte", homeDAO.findByType("sub1texte"));
        m.addAttribute("sub2text", homeDAO.findByType("sub2text"));
        m.addAttribute("sub2texte", homeDAO.findByType("sub2texte"));
        m.addAttribute("sub3text", homeDAO.findByType("sub3text"));
        m.addAttribute("sub3texte", homeDAO.findByType("sub3texte"));
        m.addAttribute("recount", homeDAO.findByType("recount"));
        m.addAttribute("recounte", homeDAO.findByType("recounte"));
        m.addAttribute("img1", homeDAO.findByType("img1"));
        m.addAttribute("img2", homeDAO.findByType("img2"));
        m.addAttribute("img3", homeDAO.findByType("img3"));
        m.addAttribute("img4", homeDAO.findByType("img4"));
        m.addAttribute("share", homeDAO.findByType("share"));
        m.addAttribute("top", homeDAO.findByType("top"));
    }
}
