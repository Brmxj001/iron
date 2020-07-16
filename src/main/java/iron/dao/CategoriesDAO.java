package iron.dao;

import iron.bean.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesDAO extends JpaRepository<Categories, Integer> {
}
