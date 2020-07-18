package iron.service;

import iron.bean.CategoriesImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
public interface CategoriesImgService {

    void add(MultipartFile file, Integer cid) throws IOException;

    void add(MultipartFile[] file, Integer cid) throws IOException;

    void delete(Integer id);

    List<CategoriesImg> getAllByCategoriesId(Integer cid);
}
