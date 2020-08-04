package iron.dao;

import iron.bean.Product;
import iron.bean.ProductAttr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wangxiaobo
 */
public interface ProductAttrDAO extends JpaRepository<ProductAttr, Integer>, JpaSpecificationExecutor<ProductAttr> {

    /**
     * pid
     * @param pid pid
     * @return List
     */
    List<ProductAttr> findByPid(Integer pid);
}
