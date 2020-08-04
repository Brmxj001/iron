package iron;

import iron.bean.Product;
import iron.bean.ProductAttr;
import iron.builder.ProductAttrBuilder;
import iron.dao.LoginDAO;
import iron.dao.ProductAttrDAO;
import iron.dao.ProductDAO;
import iron.service.impl.ProductImgServiceImpl;
import iron.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IronApplication.class)
@Slf4j
public class IronJunit {

    @Autowired
    ConfigBean bean;

    @Autowired
    ProductAttrDAO attrDAO;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    ProductDAO productDAO;
    @Autowired
    LoginDAO loginDAO;

    @Test
    public void test() {
        System.out.println(loginDAO.findByUser("user"));

    }
}
