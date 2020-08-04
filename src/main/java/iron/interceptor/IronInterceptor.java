package iron.interceptor;

import iron.dao.*;
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
    @Autowired
    ContactDAO contactDAO;

    /**
     * ForePage 页面数据
     */
    private final Set<String> forePage = new HashSet<>();

    public IronInterceptor( ) {
        forePage.add("/iron/fore/index");
        forePage.add("/iron/fore/categories");
        forePage.add("/iron/fore/categoriesSearch");
        forePage.add("/iron/fore/aboutUs");
        forePage.add("/iron/fore/product");
        forePage.add("/iron/fore/blog");
        forePage.add("/iron/fore/blogChild");
        forePage.add("/iron/fore/sitemap");
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
            modelAndView.addObject("contactUs", contactDAO.findAll());

            if (null == req.getSession().getAttribute("language")){
                req.getSession().setAttribute("language","en");
            }

        }


    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }

}
