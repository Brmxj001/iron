package iron.service.impl;

import iron.bean.ProductImg;
import iron.dao.ProductImgDAO;
import iron.service.ProductImgService;
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
public class ProductImgServiceImpl implements ProductImgService {
    @Autowired
    IronUtil ironUtil;
    @Autowired
    ProductImgDAO productImgDAO;

    @Override
    public ProductImg add(MultipartFile file, Integer pid) throws IOException {
        ProductImg img = new ProductImg();
        img.setPath(ironUtil.uploadImg(file, "ProductImg_"));
        img.setPid(pid);
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
        productImgDAO.deleteById(id);
    }

    @Override
    public ProductImg get(Integer id) {
        return productImgDAO.getOne(id);
    }

    @Override
    public List<ProductImg> getAllByProductId(Integer pid) {
        return productImgDAO.findByPid(pid);
    }
}
