package iron.bean;


import lombok.Data;

import javax.persistence.*;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "product_attr")
public class ProductAttr {


    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 属性名称
     */
    private String name;

    /**
     * 属性值
     */
    private String value;

    /**
     * 对应产品
     */
    private Integer pid;

    @Column(name = "value_en")
    private String valueEn;

    @Column(name = "name_en")
    private String nameEn;
}
