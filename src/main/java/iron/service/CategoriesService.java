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
     * @return All categories
     */
    List<E> getAll();

    /**
     * 根据字段 Categories id删除
     * @param id Categories id
     */
   void deleteCategories(Integer id);

    /**
     * 根据id获取categories,
     * @param id Categories id
     * @return Categories
     */
    E get(Integer id);

    /**
     * 保存一个Categories
     * @param e Categories
     * @return Categories
     */
    E add(E e);

    /**
     * 获取所有的Categories 并且设置ProductList
     * @return List
     */
    List<E> getAllWithProduct();

    List<E> getIndexShow();

}
