package iron.controller;


import iron.bean.ProductAttr;
import iron.dao.ProductAttrDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangxiaobo
 */
@RestController
public class ProductAttrController {

    ProductAttrDAO productAttrDAO;

    @Autowired
    public  ProductAttrController(ProductAttrDAO productAttrDAO){
        this.productAttrDAO = productAttrDAO;
    }

    @PostMapping("/back/addProductAttr")
    public void addProductAttr(ProductAttr attr){
        productAttrDAO.save(attr);
    }

    @PostMapping("/back/deleteProductAttr")
    public void doDeleteProductAttr(Integer id){
        productAttrDAO.deleteById(id);
    }

    @PostMapping("/back/getProductAttr")
    public List<ProductAttr> get(Integer pid){
        return productAttrDAO.findByPid(pid);
    }

    @PostMapping("/back/editProductAttr")
    public void edit(ProductAttr attr){
        productAttrDAO.save(attr);
    }
}
