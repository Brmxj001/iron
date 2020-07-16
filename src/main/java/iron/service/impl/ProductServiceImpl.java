package iron.service.impl;

import iron.bean.Product;
import iron.bean.ProductImg;
import iron.dao.ProductDAO;
import iron.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@Service
public class ProductServiceImpl implements ProductService<Product> {

    @Autowired
    IronUtil ironUtil;
    @Autowired
    ProductDAO productDAO;
    @Override
    public Product get() {
        return null;
    }

    @Override
    public void delete(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public Product add(Product product, MultipartFile file ) throws IOException {
        String cover = "ProductCover=" + UUID.randomUUID().toString();
        ironUtil.uploadImg(file,cover);
        product.setCover(cover);
        return productDAO.save(product);
    }

    @Override
    public void edit(Product product) {

    }


}
