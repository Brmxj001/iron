package iron.dao;

import iron.bean.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesDAO extends JpaRepository<Categories, Integer> {

    List<Categories> findByIndex(boolean isIndex);
}
