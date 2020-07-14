package iron.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import iron.bean.cimg;
import iron.dao.CImgDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iron.bean.categories;
import iron.dao.CategoriesDAO;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangxiaobo
 */
@Service
public class CategoriesServiceImpl implements CategoriesService<categories>{

    @Autowired
    CategoriesDAO categoriesDAO;
    @Autowired
    IronUtil ironUtil;
    @Autowired
    CImgDAO cImgDAO;

    @Override
    public List<categories> getAll() {
        return categoriesDAO.findAll();
    }


    public void addCImg(MultipartFile[] files, Integer cid) throws IOException {
        for (MultipartFile file : files) {
           addCImg(file,cid);
        }
    }
    @Override
    public void addCImg(MultipartFile file, Integer cid) throws IOException {
        String key = "CImg,"+"CategoriesID="+ cid +","+ UUID.randomUUID().toString();
        ironUtil.uploadImg(file,key);
        cimg cimg = new cimg();
        cimg.setPath(key);
        cimg.setCid(cid);
        cImgDAO.save(cimg);
    }



}
