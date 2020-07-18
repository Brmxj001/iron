package iron.controller;

import iron.bean.Product;
import iron.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wangxiaobo
 */
@RestController
public class ProductsController {

    @Autowired
    ProductServiceImpl productService;

    @PostMapping("/back/addProducts")
    public Product add(MultipartFile[] file, Product product, MultipartFile coverFile) throws IOException {
        return productService.add(product, coverFile);
    }

    @PostMapping("/back/addProductCover")
    public Product addProductCover(MultipartFile cover, Integer id) throws IOException {
        return productService.addProductCover(cover, id);
    }

    @PostMapping("/back/getProduct")
    public Product toProductLook(@RequestParam(value = "id") Integer id) {
        return productService.get(id);
    }


    @PostMapping("/back/editProduct")
    public void editProduct(Product p) {
        productService.edit(p);
    }


    @PostMapping("/back/deleteProduct")
    public void doProductDelete(Integer id) {
        productService.delete(id);
    }
}
