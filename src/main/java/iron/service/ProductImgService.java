package iron.service;

import iron.bean.ProductImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wangxiaobo
 */
public interface ProductImgService {

    ProductImg add(MultipartFile file, Integer pid) throws IOException;

    void add(MultipartFile[] file, Integer pid) throws IOException;

    void delete(Integer id);
}
