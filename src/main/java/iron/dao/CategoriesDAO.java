package iron.dao;

import iron.bean.categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesDAO extends JpaRepository<categories, Integer> {
}
