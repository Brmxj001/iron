package iron.builder;

import iron.bean.CategoriesImg;
import iron.bean.ProductAttr;

/**
 * @author wangxiaobo
 */
public class ProductAttrBuilder {
    ProductAttr attr;

    public ProductAttrBuilder() {
        attr = new ProductAttr();
    }

    public ProductAttrBuilder setName(String name) {
       attr.setName(name);
        return this;
    }

    public ProductAttrBuilder setValue(String value) {
       attr.setValue(value);
        return this;
    }

    public ProductAttrBuilder setNameEn(String name) {
        attr.setNameEn(name);
        return this;
    }

    public ProductAttrBuilder setValueEn(String valueEn) {
        attr.setValueEn(valueEn);
        return this;
    }

    public ProductAttrBuilder setPid(Integer id) {
        attr.setPid(id);
        return this;
    }

    public ProductAttr finish() {
        return attr;
    }

    public static ProductAttrBuilder start() {
        return new ProductAttrBuilder();
    }
}
