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
    E add(E e);




}
