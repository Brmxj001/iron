package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDAO extends JpaRepository<product, Integer>, JpaSpecificationExecutor<product> {
	List<product> findByCid(Integer cid);
	List<product> findAllByNameLike(String name);
}
