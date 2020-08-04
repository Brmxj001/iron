package iron.dao;

import iron.bean.BlogImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author wangxiaobo
 */
public interface BlogImgDAO extends JpaRepository<BlogImg, Integer>, JpaSpecificationExecutor<BlogImg> {

    List<BlogImg> findByBig(Integer big);

    void deleteByBig(Integer bid);
}
