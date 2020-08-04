package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wangxiaobo
 */
public interface ProductDAO extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	/**
	 * 根据Categories ID 获取产品
	 * @param cid  categories id
	 * @return List
	 */
	List<Product> findByCid(Integer cid);

	/**
	 * 根据name 获取列表
	 * @param name name
	 * @return List
	 */
	List<Product> findByNameLike(String name);

	/**
	 * 获取是否顶置的产品
	 * @param top @{see Product}
	 * @return List
	 */
	List<Product> findByTop(boolean top);

}
