package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductDAO extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
	List<Product> findByCid(Integer cid);
	List<Product> findAllByNameLike(String name);
}
