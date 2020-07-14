package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.cimg;

public interface CImgDAO extends JpaRepository<cimg, Integer> {
	public List<cimg> findByCid(Integer cid);
}
