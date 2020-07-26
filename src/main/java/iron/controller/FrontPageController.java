package iron.controller;

import iron.bean.contact;
import iron.bean.ProductImg;
import iron.bean.Product;
import iron.dao.*;
import iron.service.impl.ProductServiceImpl;
import iron.util.IronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangxiaobo
 */
@Slf4j
@Controller
public class FrontPageController {
    @Autowired
    ProductDAO ProductDAO;
    @Autowired
    ProductImgDAO ProductImgDAO;
    @Autowired
    ContactDAO ContactDAO;
    @Autowired
    CategoriesImgDAO categoriesImgDAO;
    @Autowired
    HomeDAO homeDAO;
    @Autowired
    HomeImgDAO homeImgDAO;
    @Autowired
    FeedbackDAO feedbackDAO;
    @Autowired
    ContactDAO contactDAO;
    @Autowired
    IronUtil ironUtil;
    private  final  ProductServiceImpl productService;
    @Autowired
    public FrontPageController(ProductServiceImpl productService){
        this.productService = productService;
    }

    @GetMapping("/front/index")
    public String toFrontIndex(Model m) {
        List<Product> products = ProductDAO.findAll(Sort.by(Sort.Direction.DESC, "callon"));
        ironUtil.doSetHome(m);
        m.addAttribute("products", products);
        m.addAttribute("carousel", homeImgDAO.findAll());
        return "front/index";
    }


    @GetMapping("/front/product")
    public String toFrontProduct(Model m, @RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "size", defaultValue = "9") Integer size) {
        Pageable pageable = PageRequest.of(start, size);

        Page<Product> page = ProductDAO.findAll(pageable);
        m.addAttribute("page", page);
        m.addAttribute("cid", 0);
        m.addAttribute("name", 0);
        m.addAttribute("resProductUrl", "/iron/front/product");
        return "front/product";
    }

    // 根据分类获得的产品
    @GetMapping("/front/product/categories")
    public String toFrontProduct(Integer cid, Model m, @RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "size", defaultValue = "9") Integer size) {
        Pageable pageable = PageRequest.of(start, size);
        Specification<Product> specification = (Root<Product> root, CriteriaQuery<?> query,
                                                CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("cid"), cid));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Page<Product> page = ProductDAO.findAll(specification, pageable);
        m.addAttribute("page", page);
        m.addAttribute("cid", cid);
        m.addAttribute("name", 0);
        m.addAttribute("resProductUrl", "/iron/front/product/categories");
        return "front/product";
    }

    // 根据字符串获得的产品
    @GetMapping("/front/product/str")
    public String toFrontProduct(String name, Model m, @RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "size", defaultValue = "9") Integer size) {
        Pageable pageable = PageRequest.of(start, size);
        Specification<Product> specification = (Root<Product> root, CriteriaQuery<?> query,
                                                CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            return criteriaBuilder.and(list.toArray(new Predicate[list.size()]));
        };
        Page<Product> page = ProductDAO.findAll(specification, pageable);
        m.addAttribute("page", page);
        m.addAttribute("name", name);
        m.addAttribute("cid", 0);
        m.addAttribute("resProductUrl", "/iron/front/product/str");
        return "front/product";
    }

    @GetMapping("/front/case")
    public String toFrontCase() {
        return "front/case";
    }


    @GetMapping("/front/feedback")
    public String toFrontFeedback(Model m) {

        return "front/feedback";
    }


    @GetMapping("/front/download")
    public String toFrontDownload() {
        return "front/download";
    }


    @GetMapping("/front/contact")
    public String toFrontContact(Model m) {
        m.addAttribute("contacts", contactDAO.findAll());
        return "front/contact";
    }

    @GetMapping("/front/prodetail")
    public String toFrontParietal(@RequestParam(value = "pid") Integer pid, Model m) {

        Product product = productService.get(pid);
        product.setAccessTotal(product.getAccessTotal() + 1);
        ProductDAO.save(product);
        List<ProductImg> imgList = ProductImgDAO.findByPid(pid);
        product.setImgList(imgList);
        List<contact> contacts = ContactDAO.findAll();
        product.setContacts(contacts);
        m.addAttribute("product", product);
        m.addAttribute("share", homeDAO.findByType("share"));

        return "front/prodetail";
    }

}
