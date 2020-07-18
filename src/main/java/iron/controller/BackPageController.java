package iron.controller;

import iron.bean.Categories;
import iron.bean.feedback;
import iron.bean.Product;
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
    @Autowired
    CategoriesServiceImpl categoriesService;
    @Autowired
    CategoriesDAO CategoriesDAO;
    @Autowired
    ProductDAO ProductDAO;
    @Autowired
    FeedbackDAO FeedbackDAO;
    @Autowired
    ContactDAO ContactDAO;
    @Autowired
    IronUtil ironUtil;

    @GetMapping("/back/index")
    public String toBackIndex() {
        return "back/products";
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
        Page<Categories> page = CategoriesDAO.findAll(pageable);
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
        Page<Product> page = ProductDAO.findAll(pageable);
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
        List<Categories> categories = CategoriesDAO.findAll();
        m.addAttribute("categories", categories);
        m.addAttribute("current", "addProducts");
        return "back/addProducts";
    }


    @GetMapping("/back/addCategoriesPage")
    public String toAddCategories(Model m) {
        m.addAttribute("current", "addcategories");
        return "back/addcategories";
    }

    @GetMapping("/back/feedbackPage")
    public String toFeedback(Model m, @RequestParam(value = "start", defaultValue = "0") Integer start,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<feedback> page = FeedbackDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());

        return "back/feedback";
    }

    @GetMapping("/back/contactPage")
    public String toBackContact(Model m) {
        m.addAttribute("contactList", ContactDAO.findAll());
        return "back/contact";
    }



}
