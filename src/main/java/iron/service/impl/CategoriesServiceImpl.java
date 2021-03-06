package iron.service.impl;

import iron.bean.Categories;
import iron.dao.CategoriesDAO;
import iron.dao.CategoriesImgDAO;
import iron.service.CategoriesImgService;
import iron.service.CategoriesService;
import iron.util.IronUtil;
import iron.util.Qiniu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @author wangxiaobo
 */
@Service
public class CategoriesServiceImpl implements CategoriesService<Categories> {

    private final CategoriesDAO categoriesDAO;
    private final CategoriesImgDAO categoriesImgDAO;
    private final ProductServiceImpl productService;
    private final IronUtil ironUtil;


    @Autowired
    public CategoriesServiceImpl(CategoriesDAO categoriesDAO, CategoriesImgDAO categoriesImgDAO, ProductServiceImpl productService, IronUtil ironUtil) {
        this.categoriesDAO = categoriesDAO;
        this.categoriesImgDAO = categoriesImgDAO;
        this.productService = productService;
        this.ironUtil = ironUtil;
    }

    @Override
    public List<Categories> getAll() {
        return categoriesDAO.findAll();
    }

    @Override
    public Categories get(Integer id) {
        Categories c = categoriesDAO.findById(id).orElse(null);
        if (null == c) return null;
        c.setImgList(categoriesImgDAO.findByCid(c.getId()));
        return c;
    }

    @Override
    public Categories add(Categories categories) {
        System.out.println(categories);
        return categoriesDAO.save(categories);
    }

    public Categories add(Categories categories, MultipartFile coverFile, MultipartFile carouselFile) throws IOException {
        categories.setCover(ironUtil.uploadImg(carouselFile, "CategoriesCover_"));
        categories.setCarousel(ironUtil.uploadImg(carouselFile, "CategoriesCarousel_"));
        return categoriesDAO.save(categories);
    }



    @Override
    public List<Categories> getAllWithProduct() {
        List<Categories> result = categoriesDAO.findAll();
        for (Categories categories : result) {
            categories.setProductList(productService.getAllByCategoriesId(categories.getId()));
            categories.setImgList(categoriesImgDAO.findByCid(categories.getId()));
        }
        System.out.println(result.size());
        return result;
    }

    @Override
    public List<Categories> getIndexShow(){
        return categoriesDAO.findByIndex(true);
    }


    @Override
    public void deleteCategories(Integer id) {
        categoriesDAO.deleteById(id);
    }


}
