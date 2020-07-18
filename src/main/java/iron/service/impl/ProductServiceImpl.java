package iron.service.impl;

import iron.bean.Product;
import iron.dao.ProductDAO;
import iron.service.ProductService;
import iron.util.IronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wangxiaobo
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService<Product> {

    @Autowired
    IronUtil ironUtil;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    ProductImgServiceImpl productImgService;

    @Override
    public Product get(Integer id) {
        Product result = productDAO.findById(id).get();
        result.setImgList(productImgService.getAllByProductId(result.getId()));
        return result;
    }

    @Override
    public Product getAll() {
        return null;
    }

    @Override
    public Product getAllByCategoriesId(Integer cid) {
        return null;
    }

    @Override
    public void delete(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public Product add(Product product, MultipartFile file) throws IOException {
        String cover = ironUtil.getUUID("ProductCover");
        ironUtil.uploadImg(file, cover);
        product.setCover(cover);
        return productDAO.save(product);
    }

    @Override
    public void edit(Product product) {
        productDAO.save(product);

    }

    public Product addProductCover(MultipartFile cover, Integer id) throws IOException {
        String key = ironUtil.getUUID("ProductCover");
        ironUtil.uploadImg(cover,key);
        Product product = productDAO.findById(id).get();
        product.setCover(key);
        return productDAO.save(product);
    }


}
