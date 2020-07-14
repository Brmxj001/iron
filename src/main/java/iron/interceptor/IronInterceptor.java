package iron.interceptor;

import iron.bean.categories;
import iron.dao.CImgDAO;
import iron.dao.CategoriesDAO;
import iron.dao.HomeDAO;
import iron.dao.ProductDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
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
        bPath.add("/iron/back/products/add");

        productPages.add("/iron/front/product/categories");
    }


    @Autowired
    ProductDAO ProductDAO;
    @Autowired
    CategoriesDAO categoriesDAO;
    @Autowired
    CImgDAO cImgDAO;
    @Autowired
    HomeDAO homeDAO;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (fspath.contains(request.getRequestURI())) {
            String lang = null;
            if (request.getCookies() != null) {
                for (Cookie c : request.getCookies()) {
                    if (c.getName().equals("i_language")) {
                        lang = c.getValue();
                        System.out.println(lang);
                    }
                }
            }
            if (lang != null) {
                request.getSession().setAttribute("i_language", lang);
            } else {
                response.addCookie(new Cookie("i_language", "zh"));
                request.getSession().setAttribute("i_language", "zh");
            }
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object o, ModelAndView modelAndView) {
        String uri = req.getRequestURI();

        if (productPages.contains(uri)) {
            int cid = Integer.parseInt(req.getParameter("cid"));
            categories categories = categoriesDAO.findById(cid).get();
            categories.setImgs(cImgDAO.findByCid(cid));
            modelAndView.addObject("cate", categories);
        }
        if (fspath.contains(uri)) {
            modelAndView.addObject("categories", categoriesDAO.findAll());
            modelAndView.addObject("top", homeDAO.findByType("top"));

        }
        if (bPath.contains(uri)) {
            modelAndView.addObject("catSize", categoriesDAO.findAll().size());
            modelAndView.addObject("proSize", ProductDAO.findAll().size());
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) {
    }

}
