package iron.interceptor;

import iron.dao.CategoriesImgDAO;
import iron.dao.CategoriesDAO;
import iron.dao.HomeDAO;
import iron.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wangxiaobo
 */
@Slf4j
public class IronInterceptor implements HandlerInterceptor {
    private Set<String> fspath = new HashSet<>();
    private Set<String> bPath = new HashSet<>();
    //sets of product page
    private Set<String> productPages = new HashSet<>();


    public IronInterceptor() {
        fspath.add("/iron/front/product");
        fspath.add("/iron/front/prodetail");
        fspath.add("/iron/front/product/categories");
        fspath.add("/iron/front/index");
        fspath.add("/iron/front/product/str");
        fspath.add("/iron/front/contact");
        fspath.add("/iron/front/feedback");

        bPath.add("/iron/back/categories");
        bPath.add("/iron/back/addcategories");
        bPath.add("/iron/back/products");

        productPages.add("/iron/front/product/categories");
    }


    @Autowired
    ProductDAO ProductDAO;
    @Autowired
    CategoriesDAO categoriesDAO;
    @Autowired
    CategoriesImgDAO categoriesImgDAO;
    @Autowired
    HomeDAO homeDAO;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object o, ModelAndView modelAndView) {
        String uri = req.getRequestURI();



    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }

}
