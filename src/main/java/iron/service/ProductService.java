package iron.service;


import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wangxiaobo
 */
public interface ProductService<E> {

    /**
     * 获取一个Product
     * @return
     */
    E get();

    /**
     * 删除Product
     * @param id
     */
    void delete(Integer id);

    /**
     * 添加一个Product
     * @param e
     * @param cover
     * @return
     * @throws IOException
     */
    E add(E e, MultipartFile cover) throws IOException;

    /**
     * 编辑Product
     * @param e
     */
    void edit(E e);
}
