package iron.bean;


import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "blog")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "name_en")
    private String nameEn;


    private String intro;
    @Column(name = "intro_en")
    private String introEn;

    @Column(name = "upload_time")
    private Date uploadTime;


    @Column(name = "create_time")
    private Date createTime;

    @Transient
    private List<BlogImg> blogImgList;
}

