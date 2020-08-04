package iron.dao;

import iron.bean.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author wangxiaobo
 */
public interface LoginDAO extends JpaRepository<Login, Integer> , JpaSpecificationExecutor<Login> {
    Login findByName(String name);
    Login findByUser(String user);


}
