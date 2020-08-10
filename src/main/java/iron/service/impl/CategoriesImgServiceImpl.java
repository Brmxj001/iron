package iron.service.impl;

import iron.bean.CategoriesImg;
import iron.builder.CategoriesImgBuilder;
import iron.dao.CategoriesImgDAO;
import iron.service.CategoriesImgService;
import iron.util.IronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Access;
import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
@Service
public class CategoriesImgServiceImpl implements CategoriesImgService {

    @Autowired
    CategoriesImgDAO categoriesImgDAO;
    @Autowired
    IronUtil ironUtil;
    @Override
    public void add(MultipartFile file, Integer cid) throws IOException {
        categoriesImgDAO.save(CategoriesImgBuilder.start().setCId(cid).setPath(ironUtil.uploadImg(file, "CategoriesImg_")).finish());
    }
    @Override
    public void add(MultipartFile[] file, Integer cid) throws IOException {
        for (MultipartFile f : file) {
            add(f, cid);
        }
    }


    @Override
    public void delete(Integer id) {
        categoriesImgDAO.deleteById(id);
    }

    @Override
    public List<CategoriesImg> getAllByCategoriesId(Integer cid) {
        return categoriesImgDAO.findByCid(cid);
    }


}
