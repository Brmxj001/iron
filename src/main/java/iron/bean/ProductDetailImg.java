package iron.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "product_detail_img")
public class ProductDetailImg {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String path;

    private Integer pid;
}
