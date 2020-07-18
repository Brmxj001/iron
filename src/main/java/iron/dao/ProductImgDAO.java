package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.ProductImg;

/**
 * @author wangxiaobo
 */
public interface ProductImgDAO extends JpaRepository<ProductImg, Integer> {
	List<ProductImg> findByPid(Integer pid);
}
