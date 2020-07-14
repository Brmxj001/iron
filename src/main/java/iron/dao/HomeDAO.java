package iron.dao;

import iron.bean.home;
import org.springframework.data.jpa.repository.JpaRepository;


public interface HomeDAO extends JpaRepository<home, Integer> {
    home findByType(String type);
}
