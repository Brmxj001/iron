package iron.controller;

import iron.bean.img;
import iron.bean.product;
import iron.dao.ImgDAO;
import iron.dao.ProductDAO;
import iron.util.Qiniu;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@RestController
@Slf4j
public class ProductsController {

    @Autowired
    ProductDAO productDAO;
    @Autowired
    ImgDAO imgDAO;

    @PostMapping("/back/products/img")
    public String saveProductImg(MultipartFile file) {
        if (file.isEmpty()) {
            log.info("empty");
        } else {
            log.info("no");
        }
        return "";
    }

    @PostMapping("/back/test")
    public String test() {
        return "test success";
    }

    @PostMapping("/back/products/addone")
    public String saveProductAdd(MultipartFile[] file, product product, MultipartFile coverFile) throws IOException {
        log.info(String.valueOf(file.length));
		String coverKey = getUuid();
		Qiniu.getQiniu().getUploadManager().put(coverFile.getBytes(), coverKey,
                Qiniu.getQiniu().getUpToken());
		product.setCallon(0);
        product.setCover(coverKey);
        Integer pid = productDAO.save(product).getId();
        for (MultipartFile m : file) {
            String key = getUuid(pid);
            saveFile(m, key);
            img img = new img();
            img.setPid(pid);
            img.setPath(key);
            imgDAO.save(img);
        }
        return "添加成功";
    }

	private String getUuid() {
		return "cover" + UUID.randomUUID().toString();
	}
	private String getUuid(Integer s) {
		return s + UUID.randomUUID().toString();
	}


    @PostMapping("/back/products/look")
    public product toProductLook(@RequestParam(value = "id") Integer id) {
        Optional<product>  opt = productDAO.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        log.info("获取单个说说失败");
        return null;
    }

    @PostMapping("/back/products/look/img")
    public List<img> toProductLookImg(@RequestParam(value = "pid") Integer pid) {
        return imgDAO.findByPid(pid);
    }

    @PostMapping("/back/products/edit")
    public void toProductLookEdit(product p) {
        productDAO.save(p);
    }

    @PostMapping("/back/products/delete/img")
    public String toProductImgDelete(@RequestParam(value = "iid") Integer iid) {
        imgDAO.deleteById(iid);
        return "success";
    }

    // 产品属性单个编辑
    @PostMapping("/back/products/solelyEdit")
    public void toProductsSolelyEdit(@RequestParam(value = "id") Integer id, String name, String prize, String intro,
                                     String model, MultipartFile cover,String namee, String introe, String modele,String prizee,String simple, String simplee) throws Exception {
        product product = productDAO.getOne(id);
        if (null != name) {
            product.setName(name);
        } else if (null != namee) {
            product.setNamee(namee);
        }else if (null != simple) {
            product.setSimple(simple);
        } else if (null != simplee) {
            product.setSimplee(simplee);
        } else if (null != prize) {
            product.setPrize(prize);
        } else if (null != prizee) {
            product.setPrizee(prizee);
        }else if (null != intro) {
            product.setIntroduction(intro);
        }else if (null != introe) {
            product.setIntroductione(introe);
        } else if (null != model) {
            product.setModel(model);
        } else if (null != modele) {
            product.setModele(modele);
        } else if (null != cover) {
			String key = getUuid();
			Qiniu.getQiniu().getUploadManager().put(cover.getBytes(), key, Qiniu.getQiniu().getUpToken());
            product.setCover(key);

        }
        productDAO.save(product);
    }

    @PostMapping("/back/img/addone")
    public void doAddOneImg(MultipartFile file, @RequestParam(value = "pid") Integer pid) {
        try {
            String key = getUuid(pid);
            saveFile(file, key);

            img img = new img();
            img.setPid(pid);
            img.setPath(key);
            imgDAO.save(img);
        } catch (IOException ignored) {
        }
    }

    private void saveFile(MultipartFile file, String key) throws IOException {
        Qiniu.getQiniu().getUploadManager().put(file.getBytes(), key, Qiniu.getQiniu().getUpToken());
    }

    @PostMapping("/back/product/delete")
    public void doProductDelete(Integer id) {
        productDAO.deleteById(id);
    }
}
