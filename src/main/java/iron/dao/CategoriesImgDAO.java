package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.CategoriesImg;

public interface CategoriesImgDAO extends JpaRepository<CategoriesImg, Integer> {
	public List<CategoriesImg> findByCid(Integer cid);
}
