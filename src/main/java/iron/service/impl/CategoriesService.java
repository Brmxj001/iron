package iron.service.impl;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
public interface CategoriesService<E> {

    /**
     * get all of categories
     * @return
     */
    List<E> getAll();

    /**
     * add img of categories
     * 1, 上传照片到七牛云,
     * 2, 添加数据到Mysql
     * @param file
     * @param cid
     */
   void addCImg(MultipartFile file,Integer cid) throws IOException;
}
