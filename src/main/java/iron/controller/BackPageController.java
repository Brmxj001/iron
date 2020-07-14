package iron.controller;

import iron.bean.categories;
import iron.bean.feedback;
import iron.bean.product;
import iron.dao.*;
import iron.service.impl.CategoriesServiceImpl;
import iron.service.impl.IronUtil;
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
public class BackPageController {
    @Autowired
    CategoriesServiceImpl CategoriesServiceImpl;
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
        return "back/index";
    }

    @GetMapping("/back/home")
    public String toBackHome(Model m) {
        ironUtil.doSetHome(m);
        return "back/home";
    }
    @GetMapping("/back/categories")
    public String toBackCategories(Model m, @RequestParam(defaultValue = "0", value = "start") Integer start,
                                   @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<categories> page = CategoriesDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());
        m.addAttribute("current", "categories");
        return "back/categories";
    }

    @GetMapping("/back/products")
    public String toBackProducts(Model m, @RequestParam(defaultValue = "0", value = "start") Integer start,
                                 @RequestParam(value = "size", defaultValue = "5") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<product> page = ProductDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());
        m.addAttribute("current", "products");
        return "back/products";
    }

    private Sort getSort() {
        return Sort.by(Sort.Direction.ASC, "id");
    }

    @GetMapping("/back/products/add")
    public String toBackProductsAdd(Model m) {
        List<categories> categories = CategoriesDAO.findAll();
        m.addAttribute("categories", categories);
        m.addAttribute("current", "addProducts");
        return "back/addProducts";
    }

    @GetMapping("/back/file")
    public String toBackProduct() {
        return "back/file";
    }

    @GetMapping("/back/addcategories")
    public String toAddCategories(Model m) {
        m.addAttribute("current", "addcategories");
        return "back/addcategories";
    }

    @GetMapping("/back/feedback")
    public String toFeedback(Model m, @RequestParam(value = "start", defaultValue = "0") Integer start,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Pageable pageable = PageRequest.of(start, size, getSort());
        Page<feedback> page = FeedbackDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("next", page.hasNext());
        m.addAttribute("pre", page.hasPrevious());

        return "back/feedback";
    }

    @GetMapping("/back/contact")
    public String toBackContact(Model m) {
        m.addAttribute("contactList", ContactDAO.findAll());
        return "back/contact";
    }



}
