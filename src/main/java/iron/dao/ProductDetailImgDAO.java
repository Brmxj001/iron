package iron.dao;

import iron.bean.Product;
import iron.bean.ProductDetailImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wangxiaobo
 */
public interface ProductDetailImgDAO extends JpaRepository<ProductDetailImg, Integer>, JpaSpecificationExecutor<Product> {
    List<ProductDetailImg> findByPid(Integer pid);
}
