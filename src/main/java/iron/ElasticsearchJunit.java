package iron;

import iron.elasticsearch.bean.Category;
import iron.elasticsearch.dao.CategoryDAO;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.snapshots.SnapshotMissingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IronApplication.class)
public class ElasticsearchJunit {
    @Autowired
    CategoryDAO dao;
    @Test
    public  void test() {
//        System.out.println(dao.findById(10001));
        System.out.println(dao.findByName("学习"));
//        elasticsearchTemplate.queryForList(new NativeSearchQueryBuilder().withQuery(QueryBuilders.matchQuery("name", "name")).build(),Category.class);
        /*FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(QueryBuilders.matchAllQuery(), ScoreFunctionBuilders.weightFactorFunction(100));

        // 设置分页
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(1, 10, sort);


       List<Category> list = dao.search(new NativeSearchQueryBuilder()
               .withPageable(pageable)
               .withQuery(functionScoreQueryBuilder)
               .build()).getContent();
        System.out.println(list);*/
    }
}
