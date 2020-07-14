package iron;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

/**
 * @author wangxiaobo
 */
@SpringBootApplication
@EnableConfigurationProperties({ConfigBean.class})
public class IronApplication {


    @Resource
    private Environment evn;

    @Resource
    private void configureThymeleafStaticVars(ThymeleafViewResolver viewResolver) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("url", evn.getProperty("iron.address"));
        map.put("qiniu", evn.getProperty("iron.qiniu"));
        viewResolver.setStaticVariables(map);
    }

    public static void main(String[] args) {
        SpringApplication.run(IronApplication.class, args);
    }
}
