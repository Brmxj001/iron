package iron.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import iron.bean.CategoriesImg;
import iron.dao.CImgDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iron.bean.Categories;
import iron.dao.CategoriesDAO;
import org.springframework.web.multipart.MultipartFile;
import iron.service.CategoriesService;

/**
 * @author wangxiaobo
 */
@Service
public class CategoriesServiceImpl implements CategoriesService<Categories> {

    @Autowired
    CategoriesDAO categoriesDAO;
    @Autowired
    IronUtil ironUtil;
    @Autowired
    CImgDAO cImgDAO;

    @Override
    public List<Categories> getAll() {
        return categoriesDAO.findAll();
    }


    @Override
    public Categories get(Integer id) {
        Categories c = categoriesDAO.findById(id).get();
        c.setImgs(cImgDAO.findByCid(c.getId()));
        return c;
    }

    @Override
    public Categories save(Categories categories) {
        return categoriesDAO.save(categories);
    }

    @Override
    public void addCategoriesImg(MultipartFile file, Integer cid) throws IOException {
        String key = "CImg," + "CategoriesID=" + cid + "," + UUID.randomUUID().toString();
        ironUtil.uploadImg(file, key);
        CategoriesImg cimg = new CategoriesImg();
        cimg.setPath(key);
        cimg.setCid(cid);
        cImgDAO.save(cimg);
    }

    @Override
    public void addCategoriesImg(MultipartFile[] files, Integer cid) throws IOException {
        for (MultipartFile file : files) {
            addCategoriesImg(file, cid);
        }
    }

    @Override
    public List<CategoriesImg> getAllCategoriesImg(Integer cid) {
        return cImgDAO.findByCid(cid);
    }

    @Override
    public void deleteCategoriesImg(Integer id) {
        cImgDAO.deleteById(id);
    }

    @Override
    public void deleteCategories(Integer id) {
        categoriesDAO.deleteById(id);
    }


}
