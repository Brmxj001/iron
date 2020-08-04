package iron.dao;

import iron.bean.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wangxiaobo
 */
public interface BlogDAO  extends JpaRepository<Blog, Integer>, JpaSpecificationExecutor<Blog> {
}
