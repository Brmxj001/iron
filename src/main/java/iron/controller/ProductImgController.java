package iron.controller;


import iron.bean.ProductImg;
import iron.service.impl.ProductImgServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductImgController {


    @Autowired
    ProductImgServiceImpl productImgService;


    @PostMapping("/back/addProductImg")
    public void addProductImg(MultipartFile[] file, Integer pid) throws IOException {
        System.out.println(file.length);
        if (null != file) {
            productImgService.add(file, pid);
        }
    }

    @PostMapping("/back/getProductImg")
    public List<ProductImg> getProductImg(Integer pid) {
        return productImgService.getAllByProductId(pid);
    }

    @PostMapping("/back/deleteProductImg")
    public void deleteProductImg(Integer id) {
        productImgService.delete(id);
    }
}
