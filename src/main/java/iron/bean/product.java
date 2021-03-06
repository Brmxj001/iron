package iron.bean;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.Data;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "product")
public class Product {


    /**
     * 唯一主键 自动增长
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer piece;
    /**
     * 产品名称 _en 表示英文名称
     */
    private String name;
    private String name_en;

    /**
     * 产品介绍
     */
    private String intro;
    private String intro_en;

    /**
     * 产品简单介绍
     */
    private String simple_intro;
    private String simple_intro_en;

    /**
     * 产品价格
     */
    private Double prize;
    @Column(name = "max_prize")
    private Integer maxPrize;

    /**
     * 产品型号
     */
    private String model;
    private String model_en;

    /**
     * Categories id
     */
    private Integer cid;

    /**
     * 封面图
     */
    private String cover;

    /**
     * 是否顶置
     */
    @Column(name = "is_top")
    private boolean top;

    @Column(name = "is_hot")
    private boolean hot;

    @Column(name = "is_new")
    private boolean New;

    private String amazon;

    /**
     * 访问次数
     */
    @Column(name = "access_total")
    private Integer accessTotal;

    /**
     * 3d模型
     */
    @Column(name = "three_d")
    private  String threeD;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "upload_time")
    private Date uploadTime;

    private Integer weight;

    @Transient
    private Categories categories;

    @Transient
    private List<ProductImg> imgList;

    @Transient
    private List<contact> contacts;
    
    @Transient
    private  List<ProductAttr> productAttrList;

    @Transient
    private List<ProductDetailImg> productDetailImgList;

    @Transient
    private  List<Product> productLikeList;
}
