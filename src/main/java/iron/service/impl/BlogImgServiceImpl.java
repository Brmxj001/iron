package iron.service.impl;

import iron.bean.BlogImg;
import iron.dao.BlogImgDAO;
import iron.service.BlogImgService;
import iron.util.IronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@Service
public class BlogImgServiceImpl implements BlogImgService {

    private final IronUtil ironUtil;
    private final BlogImgDAO blogImgDAO;

    @Autowired
    public BlogImgServiceImpl(IronUtil ironUtil, BlogImgDAO blogImgDAO) {
        this.ironUtil = ironUtil;
        this.blogImgDAO = blogImgDAO;
    }

    @Override
    public void add(Integer id, MultipartFile file) throws IOException {
        String key = "BlogImg_cid=" + id + "uuid=" + UUID.randomUUID().toString();
        ironUtil.uploadImg(file, key);
        BlogImg img = new BlogImg();
        img.setBig(id);
        img.setPath(key);
        blogImgDAO.save(img);
    }

    @Override
    public void add(Integer id, MultipartFile[] files) throws IOException {
        for (MultipartFile file : files) {
            add(id, file);
        }
    }
    @Override
    public void delete(Integer id) {
        blogImgDAO.deleteById(id);
    }


    @Override
    public List<BlogImg> getAllByGid(Integer gid) {
        return blogImgDAO.findByBig(gid);
    }

    @Override
    public void deleteAllByBig(Integer bid) {
        blogImgDAO.deleteByBig(bid);
    }


}
