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
    @Autowired
    HomeAttrDAO homeAttrDAO;

    /**
     * ForePage 页面数据
     */
    private final Set<String> forePage = new HashSet<>();
    private final Set<String> backPage = new HashSet<>();

    public IronInterceptor( ) {
        forePage.add("/iron/fore/index");
        forePage.add("/iron/fore/categories");
        forePage.add("/iron/fore/categoriesSearch");
        forePage.add("/iron/fore/aboutUs");
        forePage.add("/iron/fore/product");
        forePage.add("/iron/fore/blog");
        forePage.add("/iron/fore/blogChild");
        forePage.add("/iron/fore/sitemap");

        backPage.add("/iron/back/index");
        backPage.add("/iron/back/homePage");
        backPage.add("/iron/back/productsPage");
        backPage.add("/iron/back/categoriesPage");
        backPage.add("/iron/back/addProductPage");
        backPage.add("/iron/back/addCategoriesPage");
        backPage.add("/iron/back/feedbackPage");
        backPage.add("/iron/back/contactPage");
        backPage.add("/iron/back/blogPage");
        backPage.add("/iron/back/addBlogPage");
        backPage.add("/iron/back/userPage");
    }





    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if (backPage.contains(uri)){
            if (null == request.getSession().getAttribute("isLogin") || !(boolean) request.getSession().getAttribute("isLogin")){
                request.getSession().setAttribute("isLogin", false);
                response.sendRedirect("/iron/back/login");
            }
        }



        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object o, ModelAndView modelAndView) {
        String uri = req.getRequestURI();
        if (forePage.contains(uri)) {
            modelAndView.addObject("foreCategoriesList", categoriesService.getAll());
            modelAndView.addObject("contactUs", contactDAO.findAll());

            //foot
            modelAndView.addObject("twitter", homeAttrDAO.findByTitle("twitter"));
            modelAndView.addObject("facebook", homeAttrDAO.findByTitle("facebook"));
            modelAndView.addObject("youtube", homeAttrDAO.findByTitle("youtube"));
            modelAndView.addObject("linked", homeAttrDAO.findByTitle("linked"));
            modelAndView.addObject("email", homeAttrDAO.findByTitle("email"));
            modelAndView.addObject("followUs", homeAttrDAO.findByTitle("followUs"));
            modelAndView.addObject("foot", homeAttrDAO.findByTitle("foot"));

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
