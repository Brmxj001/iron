package iron.service;


import iron.bean.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
public interface ProductService<E> {

    /**
     * 获取一个Product
     * @param id Product id
     * @return Product
     */
    E get(Integer id);

    /**
     * 获取所有的产品
     * @return ALl Product
     */
    List<E> getAll();
    List<E> getAll(Integer total);
    List<E> getAll(Integer total,Integer cid);
    List<E> getAll(Integer total,String search);

    /**
     * 获取分类的所有产品
     * @param cid Categories id
     * @return all Categories Product
     */
    List<E> getAllByCategoriesId(Integer cid);

    /**
     * 删除Product
     * @param id Product
     */
    void delete(Integer id);

    /**
     * 添加一个Product
     * @param e Product
     * @param cover Product 封面图
     * @return Product
     * @throws IOException 上传文件
     */
    E add(E e, MultipartFile cover) throws IOException;

    /**
     * 编辑Product
     * @param e Product
     */
    void edit(E e);

    /**
     * 根据产品更新时间获取最新的产品
     * @param total 数量
     * @return Product
     */
    List<Product> getNewest(Integer total);
    List<Product> getNewest(Integer total,Integer cid);
    List<Product> getNewest(Integer total,String search);

    /**
     * 根据access_total 获取最热门的产品{@see Product}
     * @param  total 数量
     * @return Product
     */
    List<Product> getHottest(Integer total);
    List<Product> getHottest(Integer total,Integer cid);
    List<Product> getHottest(Integer total,String search);

    /**
     * 根据is_top字段获取顶置的产品
     * @return List
     */
    List<Product> getTop();

    /**
     * 由小到大,升序 asc
     * @param total 数量
     * @return List
     */
    List<Product> getByPrizeAsc(Integer total);
    List<Product> getByPrizeAsc(Integer total,Integer cid);
    List<Product> getByPrizeAsc(Integer total,String search);

    /**
     * 由大到小 降序 desc
     * @param total 数量
     * @return List
     */
    List<Product> getByPrizeDesc(Integer total);
    List<Product> getByPrizeDesc(Integer total,Integer cid);
    List<Product> getByPrizeDesc(Integer total,String search);
}
