package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.ProductImg;

public interface ProductImgDAO extends JpaRepository<ProductImg, Integer> {
	public List<ProductImg> findByPid(Integer pid);
}
