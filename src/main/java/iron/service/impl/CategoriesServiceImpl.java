package iron.service.impl;

import iron.bean.Categories;
import iron.dao.CategoriesDAO;
import iron.dao.CategoriesImgDAO;
import iron.service.CategoriesImgService;
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

    private final CategoriesDAO categoriesDAO;
    private final CategoriesImgDAO categoriesImgDAO;
    private final ProductServiceImpl productService;

    @Autowired
    public CategoriesServiceImpl(CategoriesDAO categoriesDAO,CategoriesImgDAO categoriesImgDAO,ProductServiceImpl productService){
        this.categoriesDAO = categoriesDAO;
        this.categoriesImgDAO = categoriesImgDAO;
        this.productService = productService;
    }

    @Override
    public List<Categories> getAll() {
        return categoriesDAO.findAll();
    }

    @Override
    public Categories get(Integer id) {
        Categories c = categoriesDAO.findById(id).orElse(null);
        assert c != null;
        c.setImgList(categoriesImgDAO.findByCid(c.getId()));
        return c;
    }

    @Override
    public Categories add(Categories categories) {
        return categoriesDAO.save(categories);
    }

    @Override
    public List<Categories> getAllWithProduct() {
        List<Categories> result = categoriesDAO.findAll();
        for (Categories categories : result) {
            categories.setProductList(productService.getAllByCategoriesId(categories.getId()));
        }
        return result;
    }

    @Override
    public void deleteCategories(Integer id) {
        categoriesDAO.deleteById(id);
    }



}
