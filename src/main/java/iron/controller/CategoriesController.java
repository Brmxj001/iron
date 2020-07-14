package iron.controller;

import iron.ConfigBean;
import iron.bean.categories;
import iron.bean.cimg;
import iron.dao.CImgDAO;
import iron.dao.CategoriesDAO;
import iron.service.impl.CategoriesServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class CategoriesController {

    @Autowired
    CategoriesServiceImpl CategoriesServiceImpl;
    @Autowired
    CategoriesDAO CategoriesDAO;
    @Autowired
    ResourceLoader loader;
    @Autowired
    ConfigBean bean;
    @Autowired
    CImgDAO CImgDAO;

    @PostMapping("/back/categories/look")
    public categories CategoriesLook(@RequestParam(name = "id") Integer id) {
        categories categories = CategoriesDAO.findById(id).get();
        categories.setImgs(CImgDAO.findByCid(id));
        return CategoriesDAO.findById(id).get();
    }

    //新建分类字段
    @PostMapping("/back/categories/add")
    public void doCategoriesAdd(categories c, MultipartFile[] files) throws IOException {
        Integer cid = CategoriesDAO.save(c).getId();
        CategoriesServiceImpl.addCImg(files,cid);
    }

    //添加分类照片
    @PostMapping("/back/cimg/add")
    public void doAddCImg(Integer id, MultipartFile file) throws IOException {
        CategoriesServiceImpl.addCImg(file,id);
    }

    //编辑分类字段
    @PostMapping("back/categories/edit")
    public void doCategoriesEdit(Integer id, String name, String detail, String namee) {
        categories c = CategoriesDAO.findById(id).get();
        if (name != null) {
            c.setName(name);
        } else if (null != detail) {
            c.setDetail(detail);
        } else if (null != namee) {
            c.setDetail(namee);
        }
        CategoriesDAO.save(c);
    }

    @PostMapping("/back/picture")
    public String doPicture() {
        return "success";
    }

    @PostMapping("/back/categories/delete")
    public void doCategoriesDelete(Integer id) {
        CategoriesDAO.deleteById(id);
    }

    @PostMapping("/back/cimg/getCImg")
    public List<cimg> doGetCImg(Integer id) {
        return CImgDAO.findByCid(id);
    }

    //删除分类照片
    @PostMapping("/back/cimg/delete")
    public void doDeleteCImg(Integer id) {
        CImgDAO.deleteById(id);
    }



}
