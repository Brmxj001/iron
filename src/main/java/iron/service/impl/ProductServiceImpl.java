package iron.service.impl;

import iron.bean.Product;
import iron.bean.ProductAttr;
import iron.dao.CategoriesDAO;
import iron.dao.ProductAttrDAO;
import iron.dao.ProductDAO;
import iron.dao.ProductDetailImgDAO;
import iron.service.ProductService;
import iron.util.IronUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.*;

/**
 * @author wangxiaobo
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService<Product> {

    private final IronUtil ironUtil;
    private final ProductDAO productDAO;
    private final ProductImgServiceImpl productImgService;
    private final ProductAttrDAO productAttrDAO;
    private final CategoriesDAO categoriesDAO;
    private final ProductDetailImgDAO productDetailImgDAO;


    private static final String PRODUCT_UPLOAD_TIME = "uploadTime";
    private static final String PRODUCT_ACCESS_TOTAL = "accessTotal";
    private static final String PRODUCT_PRIZE = "prize";

    @Autowired
    public ProductServiceImpl(ProductDetailImgDAO productDetailImgDAO,IronUtil ironUtil, ProductDAO productDAO, ProductImgServiceImpl productImgService, ProductAttrDAO productAttrDAO, CategoriesDAO categoriesDAO) {
        this.productDetailImgDAO = productDetailImgDAO;
        this.ironUtil = ironUtil;
        this.productDAO = productDAO;
        this.productImgService = productImgService;
        this.productAttrDAO = productAttrDAO;
        this.categoriesDAO = categoriesDAO;
    }

    @Override
    public Product get(Integer id) {
        Product result = productDAO.findById(id).orElse(null);
        assert result != null;
        result.setImgList(productImgService.getAllByProductId(result.getId()));
        result.setProductAttrList(productAttrDAO.findByPid(id));
        result.setCategories(categoriesDAO.findById(result.getCid()).get());
        result.setProductDetailImgList(productDetailImgDAO.findByPid(result.getId()));
        return result;
    }

    @Override
    public List<Product> getAll() {
        return productDAO.findAll();
    }

    @Override
    public List<Product> getAll(Integer total) {
        return productDAO.findAll(PageRequest.of(0, total)).getContent();
    }

    @Override
    public List<Product> getAll(Integer total, Integer cid) {
        return productDAO.findAll(getSpecificationEqualsCid(cid), PageRequest.of(0, total)).getContent();
    }

    @Override
    public List<Product> getAll(Integer total, String search) {
        return productDAO.findAll(getSpecificationLickName(search), PageRequest.of(0, total)).getContent();
    }

    @Override
    public List<Product> getAllByCategoriesId(Integer cid) {
        return productDAO.findByCid(cid);
    }

    @Override
    public void delete(Integer id) {
        productDAO.deleteById(id);
    }

    @Override
    public Product add(Product product, MultipartFile file) throws IOException {
        product.setCover( ironUtil.uploadImg(file, "ProductCover_"));
        product.setAccessTotal(0);
        product.setCreateTime(new Date());
        product.setUploadTime(new Date());
        return productDAO.save(product);
    }

    @Override
    public void edit(Product p) {
        p.setUploadTime(new Date());
        productDAO.save(p);

    }

    @Override
    public List<Product> getNewest(Integer total) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_UPLOAD_TIME));
        return productDAO.findAll(pageable).getContent();
    }

    @Override
    public List<Product> getHottest(Integer total) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_ACCESS_TOTAL));
        return productDAO.findAll(pageable).getContent();
    }

    @Override
    public List<Product> getTop() {
        return productDAO.findByTop(true);
    }

    @Override
    public List<Product> getByPrizeAsc(Integer total) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.ASC, PRODUCT_PRIZE));
        return productDAO.findAll(pageable).getContent();
    }

    @Override
    public List<Product> getByPrizeDesc(Integer total) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_PRIZE));
        return productDAO.findAll(pageable).getContent();
    }

    /**
     * cid equals
     */
    @Override
    public List<Product> getHottest(Integer total, Integer cid) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_ACCESS_TOTAL));
        return productDAO.findAll(getSpecificationEqualsCid(cid), pageable).getContent();
    }

    @Override
    public List<Product> getNewest(Integer total, Integer cid) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_UPLOAD_TIME));
        return productDAO.findAll(getSpecificationEqualsCid(cid), pageable).getContent();
    }

    @Override
    public List<Product> getByPrizeAsc(Integer total, Integer cid) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.ASC, PRODUCT_PRIZE));
        return productDAO.findAll(getSpecificationEqualsCid(cid), pageable).getContent();
    }

    @Override
    public List<Product> getByPrizeDesc(Integer total, Integer cid) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_PRIZE));
        return productDAO.findAll(getSpecificationEqualsCid(cid), pageable).getContent();
    }

    /**
     * name like
     */
    @Override
    public List<Product> getHottest(Integer total, String search) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_ACCESS_TOTAL));
        return productDAO.findAll(getSpecificationLickName(search), pageable).getContent();
    }

    @Override
    public List<Product> getNewest(Integer total, String search) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_UPLOAD_TIME));
        return productDAO.findAll(getSpecificationLickName(search), pageable).getContent();
    }

    @Override
    public List<Product> getByPrizeAsc(Integer total, String search) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.ASC, PRODUCT_PRIZE));
        return productDAO.findAll(getSpecificationLickName(search), pageable).getContent();
    }

    @Override
    public List<Product> getByPrizeDesc(Integer total, String search) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, PRODUCT_PRIZE));
        return productDAO.findAll(getSpecificationLickName(search), pageable).getContent();
    }

    /**
     * cid equals 条件查询器
     *
     * @param cid cid
     * @return Specification
     */
    public Specification<Product> getSpecificationEqualsCid(Integer cid) {
        return (Root<Product> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.equal(root.get("cid"), cid));
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
    }

    /**
     * name lick 条件查询器
     *
     * @param name name
     * @return Specification
     */
    public Specification<Product> getSpecificationLickName(String name) {
        return (Root<Product> root, CriteriaQuery<?> query,
                CriteriaBuilder criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            list.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
    }


    public Product addProductCover(MultipartFile cover, Integer id) throws IOException {
        Product product = productDAO.findById(id).orElse(null);
        // 如果 true 程序继续执行
        assert product != null;
        product.setCover( ironUtil.uploadImg(cover, "ProductCover_"));
        return productDAO.save(product);
    }

    @Override
    public List<Product> getByNameLike(String name) {
        return productDAO.findByNameLike("%" + name + "%");
    }

    @Override
    public List<Product> getByNameLike(String[] name) {
        Set<Product> set = new HashSet<>();
        for (String s : name) {
            set.addAll(getByNameLike(s));

        }
        return new ArrayList<>(set);
    }


}
