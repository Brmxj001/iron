package iron.bean;


import lombok.Data;

import javax.persistence.*;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "blog_img")
public class BlogImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String path;

    private Integer big;
}
