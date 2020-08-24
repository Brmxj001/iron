package iron.elasticsearch.bean;


import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author wangxiaobo
 */
@Document(indexName = "how2java",type = "category")
@Data
public class Category {
    private int id;

    private String name;
}
