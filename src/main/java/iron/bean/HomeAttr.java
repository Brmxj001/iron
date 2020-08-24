package iron.bean;


import lombok.Data;

import javax.persistence.*;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "home_attr")
public class HomeAttr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String content;

}
