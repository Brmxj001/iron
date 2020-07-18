package iron.bean;

import java.util.List;

import javax.persistence.*;

import lombok.Data;

/**
 * @author wangxiaobo
 */
@Entity
@Data
@Table(name = "categories")
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String detail;
	private String namee;

	@Transient
	private List<CategoriesImg> imgList
			;

}
