package iron.dao;

import iron.bean.HomeAttr;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangxiaobo
 */
public interface HomeAttrDAO extends JpaRepository<HomeAttr, Integer> {

    HomeAttr findByTitle(String attr);
}
