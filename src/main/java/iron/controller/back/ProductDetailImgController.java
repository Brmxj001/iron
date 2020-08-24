package iron.controller.back;

import iron.bean.ProductDetailImg;
import iron.bean.ProductImg;
import iron.dao.ProductDetailImgDAO;
import iron.service.impl.ProductImgServiceImpl;
import iron.util.IronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
public class ProductDetailImgController {
    @Autowired
    private  ProductDetailImgDAO productDetailImgDAO;
    @Autowired
    private  IronUtil ironUtil;


    @PostMapping("/back/addProductDetailImg")
    public void addProductImg(MultipartFile[] file, Integer pid) throws IOException {
        for (MultipartFile multipartFile : file) {
           String key =  ironUtil.uploadImg(multipartFile, "ProductDetailImg_");
            ProductDetailImg img = new ProductDetailImg();
            img.setPath(key);
            img.setPid(pid);
            productDetailImgDAO.save(img);
        }

    }

    @PostMapping("/back/deleteProductDetailImg")
    public void deleteProductImg(Integer id){
        productDetailImgDAO.deleteById(id);
    }

    @PostMapping("/back/getProductDetailImg")
    public List<ProductDetailImg> getProductDetailImg(Integer id){
        System.out.println(productDetailImgDAO.findByPid(id));
        return productDetailImgDAO.findByPid(id);
    }
}
