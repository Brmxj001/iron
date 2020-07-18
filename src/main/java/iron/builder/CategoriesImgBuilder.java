package iron.builder;

import iron.bean.Categories;
import iron.bean.CategoriesImg;

/**
 * @author wangxiaobo
 */
public class CategoriesImgBuilder {
    CategoriesImg img;

    public CategoriesImgBuilder() {
        img = new CategoriesImg();
    }

    public CategoriesImgBuilder setCId(Integer cid) {
        img.setCid(cid);
        return this;
    }

    public CategoriesImgBuilder setPath(String path) {
        img.setPath(path);
        return this;
    }

    public CategoriesImg finish() {
        return img;
    }

    public static CategoriesImgBuilder start() {
        return new CategoriesImgBuilder();
    }
}
