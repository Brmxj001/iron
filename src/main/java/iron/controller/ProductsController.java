package iron.controller;

import iron.bean.ProductImg;
import iron.bean.Product;
import iron.dao.ProductImgDAO;
import iron.dao.ProductDAO;
import iron.service.impl.ProductImgServiceImpl;
import iron.service.impl.ProductServiceImpl;
import iron.util.Qiniu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@RestController
@Slf4j
public class ProductsController {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    ProductImgDAO productImgDAO;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductImgServiceImpl productImgService;

    @PostMapping("/back/products/img")
    public String saveProductImg(MultipartFile file) {
        if (file.isEmpty()) {
            log.info("empty");
        } else {
            log.info("no");
        }
        return "";
    }

    @PostMapping("/back/test")
    public String test() {
        return "test success";
    }

    @PostMapping("/back/addProducts")
    public Product add(MultipartFile[] file, Product product, MultipartFile coverFile) throws IOException {
        return productService.add(product, coverFile);
    }

    @PostMapping("/back/addProductImg")
    public void addProductImg(MultipartFile[] file, Integer pid) throws IOException {
        productImgService.add(file, pid);
    }

    private String getUuid() {
        return "cover" + UUID.randomUUID().toString();
    }

    private String getUuid(Integer s) {
        return s + UUID.randomUUID().toString();
    }


    @PostMapping("/back/products/look")
    public Product toProductLook(@RequestParam(value = "id") Integer id) {
        Optional<Product> opt = productDAO.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        log.info("获取单个说说失败");
        return null;
    }

    @PostMapping("/back/products/look/img")
    public List<ProductImg> toProductLookImg(@RequestParam(value = "pid") Integer pid) {
        return productImgDAO.findByPid(pid);
    }

    @PostMapping("/back/products/edit")
    public void toProductLookEdit(Product p) {
        productDAO.save(p);
    }

    @PostMapping("/back/products/delete/img")
    public String toProductImgDelete(@RequestParam(value = "iid") Integer iid) {
        productImgDAO.deleteById(iid);
        return "success";
    }

    // 产品属性单个编辑
    @PostMapping("/back/products/solelyEdit")
    public void toProductsSolelyEdit(Product p) throws Exception {

        productDAO.save(p);
    }


    @PostMapping("/back/img/addone")
    public void doAddOneImg(MultipartFile file, @RequestParam(value = "pid") Integer pid) {
        try {
            String key = getUuid(pid);
            saveFile(file, key);

            ProductImg img = new ProductImg();
            img.setPid(pid);
            img.setPath(key);
            productImgDAO.save(img);
        } catch (IOException ignored) {
        }
    }

    private void saveFile(MultipartFile file, String key) throws IOException {
        Qiniu.getQiniu().getUploadManager().put(file.getBytes(), key, Qiniu.getQiniu().getUpToken());
    }

    @PostMapping("/back/deleteProduct")
    public void doProductDelete(Integer id) {
        productService.delete(id);
    }
}
