package iron.dao;

import iron.bean.homeimg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author wangxiaobo
 */
public interface HomeImgDAO extends JpaRepository<homeimg, Integer> {
}
