package iron.controller;


import iron.bean.Categories;
import iron.bean.Product;
import iron.bean.feedback;
import iron.dao.FeedbackDAO;
import iron.dao.HomeImgDAO;
import iron.service.CategoriesService;
import iron.service.ProductService;
import iron.service.impl.CategoriesServiceImpl;
import iron.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wangxiaobo
 */
@Controller
public class ForePageController {


    private final HomeImgDAO homeImgDAO;
    private final ProductService<Product> productService;
    private final CategoriesService<Categories> categoriesService;
    private final FeedbackDAO feedbackDAO;
    /**
     * 分类排序方式
     */
    private final static String PRIZE_ASC = "prizeAsc";
    private final static String PRIZE_DESC = "prizeDesc";
    private final static String POPULAR = "popular";
    private final static String SALE = "sale";
    private final static String NEWEST = "newest";

    @Autowired
    public ForePageController(HomeImgDAO homeImgDAO, ProductServiceImpl productService, CategoriesServiceImpl categoriesService, FeedbackDAO feedbackDAO) {
        this.homeImgDAO = homeImgDAO;
        this.productService = productService;
        this.categoriesService = categoriesService;
        this.feedbackDAO = feedbackDAO;
    }

    @GetMapping("/fore/index")
    public String toIndexPage(Model m) {
        m.addAttribute("homeImgList", homeImgDAO.findAll());
        m.addAttribute("newest", productService.getNewest(14));
        m.addAttribute("hottest", productService.getHottest(14));
        m.addAttribute("top", productService.getTop());
        m.addAttribute("categoriesListWithProduct", categoriesService.getAllWithProduct());
        return "fore/index";
    }

    @GetMapping("/fore/categories")
    public String toCategoriesPage(@RequestParam(defaultValue = "200") Integer total, @RequestParam(defaultValue = "relevance") String way, Integer cid, Model m) {
        List<Product> list;
        switch (way) {
            case PRIZE_ASC -> list = productService.getByPrizeAsc(total,cid);
            case PRIZE_DESC -> list = productService.getByPrizeDesc(total,cid);
            case NEWEST -> list = productService.getNewest(total,cid);
            case POPULAR, SALE -> list = productService.getHottest(total,cid);
            default -> list = productService.getAll(total,cid);
        }
        Categories c = categoriesService.get(cid);
        c.setProductList(list);
        m.addAttribute("categories", c);
        m.addAttribute("total", total);
        m.addAttribute("way", way);
        return "fore/categories";
    }

    @GetMapping("/fore/categoriesSearch")
    public String toCategoriesSearchPage(@RequestParam(defaultValue = "200") Integer total, @RequestParam(defaultValue = "relevance") String way,@RequestParam(defaultValue = "all")String searchOrAll, Model m) {
        System.out.println(searchOrAll);
        m.addAttribute("productList", searchOrAll(total,way,searchOrAll));
        m.addAttribute("searchOrAll", searchOrAll);
        m.addAttribute("total", total);
        m.addAttribute("way", way);
        return "fore/categoriesSearch";
    }

    public List<Product> searchOrAll(Integer total,String way,String searchOrAll){
        List<Product> list;
        //all 代表获取所有的, search 代表根据关键词获取
        if ("all".equals(searchOrAll)){
            System.out.println("all");
            switch (way) {
                case PRIZE_ASC -> list = productService.getByPrizeAsc(total);
                case PRIZE_DESC -> list = productService.getByPrizeDesc(total);
                case NEWEST -> list = productService.getNewest(total);
                case POPULAR, SALE -> list = productService.getHottest(total);
                default -> list = productService.getAll(total);
            }
        }else {
            System.out.println("name");
            System.out.println("a");
            switch (way) {
                case PRIZE_ASC -> list = productService.getByPrizeAsc(total,searchOrAll);
                case PRIZE_DESC -> list = productService.getByPrizeDesc(total,searchOrAll);
                case NEWEST -> list = productService.getNewest(total,searchOrAll);
                case POPULAR, SALE -> list = productService.getHottest(total,searchOrAll);
                default -> list = productService.getAll(total,searchOrAll);
            }
        }
        return list;
    }

    @GetMapping("/fore/feedback")
    public String toFeedbackPage() {
        return "fore/feedback";
    }

    @PostMapping("/fore/addFeedback")
    public String doAddFeedbcak(feedback f, Model m) {
        m.addAttribute("success", true);
        feedbackDAO.save(f);
        return "fore/feedback";
    }

    @GetMapping("/fore/sitemap")
    public String toSitemapPage(Model m) {
        m.addAttribute("categories", categoriesService.getAllWithProduct());
        return "fore/sitemap";
    }

    @GetMapping("/fore/blog")
    public String toBlogPage(){
        return "fore/blog";
    }

    @GetMapping("/fore/test")
    public String toTextPage(){
        return "fore/test";
    }

    @GetMapping("/fore/aboutUs")
    public String toAboutUsPage(){
        return "fore/aboutUs";
    }
}
