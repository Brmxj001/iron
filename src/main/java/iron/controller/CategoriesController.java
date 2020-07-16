package iron.controller;

import iron.bean.Categories;
import iron.bean.CategoriesImg;
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

    @PostMapping("/back/getCategories")
    public Categories get(@RequestParam(name = "id") Integer id) {
        return CategoriesServiceImpl.get(id);
    }

    @PostMapping("/back/addCategories")
    public void add(Categories c, MultipartFile[] files) throws IOException {
        Integer cid = CategoriesServiceImpl.save(c).getId();
        CategoriesServiceImpl.addCategoriesImg(files, cid);
    }

    @PostMapping("back/editCategories")
    public void edit(Categories c) {
        CategoriesServiceImpl.save(c);
    }

    @PostMapping("/back/deleteCategories")
    public void delete(Integer id) {
        CategoriesServiceImpl.deleteCategories(id);
    }

    @PostMapping("/back/getCategoriesImgUrl")
    public List<CategoriesImg> getCategoriesImg(Integer cid) {
        return CategoriesServiceImpl.getAllCategoriesImg(cid);
    }

    @PostMapping("/back/deleteCategoriesImgUrl")
    public void deleteCategoriesImg(Integer id) {
        CategoriesServiceImpl.deleteCategoriesImg(id);
    }

    @PostMapping("/back/addCategoriesImgUrl")
    public void addCategoriesImg(Integer cid, MultipartFile file) throws IOException {
        if (null != file) {
            CategoriesServiceImpl.addCategoriesImg(file, cid);
        }
    }
}
