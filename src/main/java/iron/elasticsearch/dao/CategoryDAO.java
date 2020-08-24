package iron.elasticsearch.dao;

import iron.elasticsearch.bean.Category;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @author wangxiaobo
 */

public interface CategoryDAO  extends ElasticsearchRepository<Category,Integer> {

    List<Category> findByName(String name);

}
