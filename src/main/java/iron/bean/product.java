package iron.bean;

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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String name_en;

    private String intro;
    private String intro_en;

    private String simple_intro;
    private String simple_intro_en;

    private String prize;
    private String prize_en;

    private String model;
    private String model_en;

    private Integer cid;

    private String cover;

    private Integer access_total;

    @Transient
    private List<ProductImg> imgs;

    @Transient
    private List<contact> contacts;

}
