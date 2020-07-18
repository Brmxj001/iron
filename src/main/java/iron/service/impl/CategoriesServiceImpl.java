package iron.service.impl;

import iron.bean.Categories;
import iron.dao.CategoriesDAO;
import iron.dao.CategoriesImgDAO;
import iron.service.CategoriesService;
import iron.util.IronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
@Service
public class CategoriesServiceImpl implements CategoriesService<Categories> {

    @Autowired
    CategoriesDAO categoriesDAO;
    @Autowired
    CategoriesImgDAO categoriesImgDAO;

    @Override
    public List<Categories> getAll() {
        return categoriesDAO.findAll();
    }

    @Override
    public Categories get(Integer id) {
        Categories c = categoriesDAO.findById(id).get();
        c.setImgList(categoriesImgDAO.findByCid(c.getId()));
        return c;
    }

    @Override
    public Categories add(Categories categories) {
        return categoriesDAO.save(categories);
    }

    @Override
    public void deleteCategories(Integer id) {
        categoriesDAO.deleteById(id);
    }


}
