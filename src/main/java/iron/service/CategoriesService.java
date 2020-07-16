package iron.service;

import iron.bean.CategoriesImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
public interface CategoriesService<E> {

    /**
     * 获取所有Categories
     * @return
     */
    List<E> getAll();

    /**
     * 根据字段 Categories id删除
     * @param id
     */
   void deleteCategories(Integer id);

    /**
     * 根据id获取categories,
     * @param id
     * @return
     */
    E get(Integer id);

    /**
     * 保存一个Categories
     * @param e
     * @return
     */
    E save(E e);


    /**
     * 添加Categories Img
     * 1, 上传照片到七牛云,
     * 2, 添加数据到Mysql
     * @param file
     * @param cid
     */
    void addCategoriesImg(MultipartFile file,Integer cid) throws IOException;

    /**
     * 添加Categories Img
     * @param file
     * @param cid
     * @throws IOException
     */
    void addCategoriesImg(MultipartFile[] file,Integer cid) throws IOException;

    /**
     * 根据Categories id获取img
     * @param cid
     * @return
     */
    List<CategoriesImg> getAllCategoriesImg(Integer cid);

    /**
     * 根据cimg字段的id 删除字段
     * @param id
     */
    void deleteCategoriesImg(Integer id);
}
