package iron.service.impl;

import iron.bean.ProductImg;
import iron.dao.ProductImgDAO;
import iron.service.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@Service
public class ProductImgServiceImpl implements ProductImgService {
    @Autowired
    IronUtil ironUtil;
    @Autowired
    ProductImgDAO productImgDAO;
    @Override
    public ProductImg add(MultipartFile file, Integer pid) throws IOException {
        ProductImg img = new ProductImg();
        String key = ironUtil.getUUID("ProductImg");
        img.setPath(key);
        img.setPid(pid);
        ironUtil.uploadImg(file,key);
        return productImgDAO.save(img);
    }

    @Override
    public void add(MultipartFile[] file, Integer pid) throws IOException {
        for (MultipartFile f : file) {
            add(f, pid);
        }
    }

    @Override
    public void delete(Integer id) {

    }
}