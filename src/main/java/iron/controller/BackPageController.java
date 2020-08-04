package iron.controller;

import iron.bean.*;
import iron.dao.*;
import iron.service.impl.CategoriesServiceImpl;
import iron.util.IronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Slf4j
public class BackPageController {


    private final CategoriesServiceImpl categoriesService;
    private final CategoriesDAO categoriesDAO;
    private final ProductDAO productDAO;
    private final FeedbackDAO feedbackDAO;
    private final ContactDAO contactDAO;
    private final IronUtil ironUtil;

    private final BlogDAO blogDAO;

    @Autowired
    public BackPageController(BlogDAO blogDAO,
                              ProductDAO productDAO,
                              CategoriesServiceImpl categoriesService,
                              CategoriesDAO categoriesDAO,
                              IronUtil ironUtil,
                              ContactDAO contactDAO,
                              FeedbackDAO feedbackDAO) {
        this.blogDAO = blogDAO;
        this.categoriesService = categoriesService;
        this.categoriesDAO = categoriesDAO;
        this.productDAO = productDAO;
        this.feedbackDAO = feedbackDAO;
        this.contactDAO = contactDAO;
        this.ironUtil = ironUtil;
    }

    @GetMapping("/back/index")
    public String toBackIndex() {
        return "redirect:productsPage";
    }

    @GetMapping("/back/homePage")
    public String toBackHome(Model m) {
        ironUtil.doSetHome(m);
        return "back/home";
    }

    @GetMapping("/back/categoriesPage")
    public String toBackCategories(Model m, @RequestParam(defaultValue = "0", value = "start") Integer start,
                                   @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<Categories> page = categoriesDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());
        m.addAttribute("current", "categories");
        return "back/categories";
    }

    @GetMapping("/back/productsPage")
    public String toBackProducts(Model m, @RequestParam(defaultValue = "0", value = "start") Integer start,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<Product> page = productDAO.findAll(pageable);
        log.info(page.getContent().toString());
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());
        m.addAttribute("current", "products");
        m.addAttribute("categories", categoriesService.getAll());
        return "back/products";
    }

    private Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "id");
    }

    @GetMapping("/back/addProductPage")
    public String toBackProductsAdd(Model m) {
        List<Categories> categories = categoriesDAO.findAll();
        m.addAttribute("categories", categories);
        m.addAttribute("current", "addProducts");
        return "back/addProducts";
    }


    @GetMapping("/back/addCategoriesPage")
    public String toAddCategories(Model m) {
        m.addAttribute("current", "addCategories");
        return "back/addCategories";
    }

    @GetMapping("/back/feedbackPage")
    public String toFeedback(Model m, @RequestParam(value = "start", defaultValue = "0") Integer start,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<feedback> page = feedbackDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());

        return "back/feedback";
    }

    @GetMapping("/back/contactPage")
    public String toBackContact(Model m) {
        m.addAttribute("contactList", contactDAO.findAll());
        return "back/contact";
    }


    @GetMapping("/back/addBlogPage")
    public String toAddBlogPage(Model m) {
        m.addAttribute("current", "addBlogPage");
        return "back/addBlog";
    }

    @GetMapping("/back/blogPage")
    public String toBlogPage(Model m, @RequestParam(defaultValue = "0") Integer start, @RequestParam(defaultValue = "5") Integer size) {

        Page<Blog> page = blogDAO.findAll(PageRequest.of(start, size));
        m.addAttribute("current", "blogPage");
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());
        return "back/blog";
    }

}
