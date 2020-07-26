package iron.interceptor;

import iron.dao.CategoriesImgDAO;
import iron.dao.CategoriesDAO;
import iron.dao.HomeDAO;
import iron.dao.ProductDAO;
import iron.service.impl.CategoriesServiceImpl;
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


    @Autowired
    CategoriesServiceImpl categoriesService;

    /**
     * ForePage 页面数据
     */
    private final Set<String> forePage = new HashSet<>();

    public IronInterceptor( ) {
        forePage.add("/iron/fore/index");
        forePage.add("/iron/fore/categories");
        forePage.add("/iron/fore/aboutUs");
    }





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object o, ModelAndView modelAndView) {
        String uri = req.getRequestURI();
        if (forePage.contains(uri)) {
            modelAndView.addObject("foreCategoriesList", categoriesService.getAll());
        }


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }

}
