package iron.controller;

import iron.bean.Blog;
import iron.dao.BlogDAO;
import iron.service.BlogService;
import iron.service.impl.BlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author wangxiaobo
 */
@RestController
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogServiceImpl blogService) {
        this.blogService = blogService;
    }

    @PostMapping("/back/addBlog")
    public void addBlog(Blog blog, MultipartFile[] file) throws IOException {
        blog.setCreateTime(new Date());
        blog.setUploadTime(new Date());
        blogService.add(blog, file);
    }

    @PostMapping("/back/deleteBlog")
    public void deleteBlog(Integer id) {
        blogService.delete(id);
    }

    @PostMapping("/back/getBlog")
    public Blog getBlog(Integer id) {
        return blogService.get(id);
    }

    @PostMapping("/back/editBlog")
    public void editBlog(Blog blog) {
        blogService.edit(blog);
    }


}
