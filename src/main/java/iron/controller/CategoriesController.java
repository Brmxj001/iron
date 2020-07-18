package iron.controller;

import iron.bean.Categories;
import iron.bean.CategoriesImg;
import iron.service.CategoriesImgService;
import iron.service.impl.CategoriesImgServiceImpl;
import iron.service.impl.CategoriesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

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

    @PostMapping("/back/getCategories")
    public Categories get(@RequestParam(name = "id") Integer id) {
        return CategoriesServiceImpl.get(id);
    }

    @PostMapping("/back/addCategories")
    public void add(Categories c, MultipartFile[] files) throws IOException {
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
