package iron.controller;

import iron.bean.Categories;
import iron.bean.CategoriesImg;
import iron.service.CategoriesImgService;
import iron.service.impl.CategoriesImgServiceImpl;
import iron.service.impl.CategoriesServiceImpl;
import iron.util.IronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@RestController
@Slf4j
public class CategoriesController {

    @Autowired
    CategoriesServiceImpl CategoriesServiceImpl;
    @Autowired
    CategoriesImgServiceImpl categoriesImgService;
    @Autowired
    IronUtil ironUtil;

    @PostMapping("/back/getCategories")
    public Categories get(@RequestParam(name = "id") Integer id) {
        return CategoriesServiceImpl.get(id);
    }

    @PostMapping("/back/addCategories")
    public void add(Categories c, MultipartFile[] files, MultipartFile carouselFile) throws IOException {
        String key = "carousel>"+ Calendar.getInstance().getTime();
        ironUtil.uploadImg(carouselFile,key);
        c.setCarousel(key);
        Integer cid = CategoriesServiceImpl.add(c).getId();
        categoriesImgService.add(files, cid);
    }

    @PostMapping("back/editCategories")
    public void edit(Categories c) {
        CategoriesServiceImpl.add(c);
    }

    @PostMapping("/back/deleteCategories")
    public void delete(Integer id) {
        CategoriesServiceImpl.deleteCategories(id);
    }


}
