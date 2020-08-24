package iron.controller.back;

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
import java.util.*;

/**
 * @author wangxiaobo
 */
@RestController
@Slf4j
public class CategoriesController {

    @Autowired
    CategoriesServiceImpl categoriesServiceImpl;
    @Autowired
    CategoriesImgServiceImpl categoriesImgService;
    @Autowired
    IronUtil ironUtil;

    @PostMapping("/back/getCategories")
    public Categories get(@RequestParam(name = "id") Integer id) {
        return categoriesServiceImpl.get(id);
    }

    @PostMapping("/back/addCategories")
    public Categories add(Categories c, MultipartFile coverFile, MultipartFile carouselFile) throws IOException {
        c.setCover(ironUtil.uploadImg(coverFile, "CategoriesCover_"));
        System.out.println("第一次"+c);
        return categoriesServiceImpl.add(c);
    }

    @PostMapping("/back/editCategories")
    public void edit(Categories c) {
        categoriesServiceImpl.add(c);
    }

    @PostMapping("/back/uploadFile")
    public String uploadFile(MultipartFile file,String key) throws IOException {
        return ironUtil.uploadImg(file, key);
    }


    @PostMapping("/back/deleteCategories")
    public void delete(Integer id) {
        categoriesServiceImpl.deleteCategories(id);
    }


}
