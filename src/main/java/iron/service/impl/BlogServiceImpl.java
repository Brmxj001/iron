package iron.service.impl;

import iron.bean.Blog;
import iron.dao.BlogDAO;
import iron.service.BlogImgService;
import iron.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxiaobo
 */
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogImgService blogImgService;
    private final BlogDAO blogDAO;

    @Autowired
    public BlogServiceImpl(BlogDAO blogDAO, BlogImgServiceImpl blogImgService) {
        this.blogImgService = blogImgService;
        this.blogDAO = blogDAO;
    }

    @Override
    public void add(Blog blog, MultipartFile[] files) throws IOException {
        blogImgService.add(blogDAO.save(blog).getId(), files);
    }

    @Override
    public void delete(Integer id) {
        blogDAO.deleteById(id);
        blogImgService.deleteAllByBig(id);
    }

    @Override
    public Blog get(Integer id) {
        Blog blog = blogDAO.findById(id).orElse(null);
        assert blog != null;
        setBlogImgList(blog);
        return blog;
    }

    @Override
    public List<Blog> getLatest(Integer total) {
        Pageable pageable = PageRequest.of(0, total, Sort.by(Sort.Direction.DESC, "createTime"));
        List<Blog> result = blogDAO.findAll(pageable).getContent();
        setBlogImgList(result);
        return result;
    }

    @Override
    public List<Blog> getAll() {
        List<Blog> blogList = blogDAO.findAll();
        setBlogImgList(blogList);
        return blogList;
    }

    @Override
    public void edit(Blog blog) {
        blogDAO.save(blog);
    }

    private void setBlogImgList(List<Blog> blogList){
        for (Blog blog : blogList) {
            setBlogImgList(blog);
        }
    }

    private void setBlogImgList(Blog blog) {
        blog.setBlogImgList(blogImgService.getAllByGid(blog.getId()));
    }
}
