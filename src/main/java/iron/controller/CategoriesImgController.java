package iron.controller;


import iron.bean.CategoriesImg;
import iron.service.impl.CategoriesImgServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
@RestController
@Slf4j
public class CategoriesImgController {


    @Autowired
    CategoriesImgServiceImpl categoriesImgService;

    @PostMapping("/back/getCategoriesImg")
    public List<CategoriesImg> get(Integer cid) {
        List<CategoriesImg> list = categoriesImgService.getAllByCategoriesId(cid);
        log.info(list.toString());
        return list;


    }

    @PostMapping("/back/deleteCategoriesImg")
    public void delete(Integer id) {
        categoriesImgService.delete(id);
    }

    @PostMapping("/back/addCategoriesImg")
    public void add(Integer cid, MultipartFile file) throws IOException {
        if (null != file) {
            categoriesImgService.add(file, cid);
        }
    }
}
