package iron.service;

import iron.bean.ProductImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
public interface ProductImgService {

    /**
     * 添加照片
     * @param file
     * @param pid
     * @return
     * @throws IOException
     */
    ProductImg add(MultipartFile file, Integer pid) throws IOException;

    /**
     * 添加照片
     * @param file
     * @param pid
     * @throws IOException
     */
    void add(MultipartFile[] file, Integer pid) throws IOException;

    /**
     * 删除Product Img
     * @param id
     */
    void delete(Integer id);

    /**
     * 获取Product Img
     * @param id
     * @return
     */
    ProductImg get(Integer id);

    /**
     * 获取产品照片
     * @param pid
     * @return
     */
    List<ProductImg> getAllByProductId(Integer pid);
}
