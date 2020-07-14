package iron.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import iron.bean.img;

public interface ImgDAO extends JpaRepository<img, Integer> {
	public List<img> findByPid(Integer pid);
}
