package iron.controller;


import iron.bean.BlogImg;
import iron.service.BlogImgService;
import iron.service.impl.BlogImgServiceImpl;
import iron.util.IronUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxiaobo
 */
@RestController
public class BlogImgController {

    private final BlogImgService blogImgService;
    @Autowired
    public BlogImgController(IronUtil ironUtil, BlogImgServiceImpl blogImgService){
        this.blogImgService = blogImgService;
    }


    @PostMapping("/back/addBlogImg")
    public void addBlogImg(Integer id, MultipartFile file) throws IOException {
        blogImgService.add(id,file);
    }

    @PostMapping("/back/deleteBlogImg")
    public void deleteBlogImg(Integer id) {
        blogImgService.delete(id);
    }

    @PostMapping("/back/getBlogImg")
    public List<BlogImg> getBlogImg(Integer id) {
        return blogImgService.getAllByGid(id);
    }
}
