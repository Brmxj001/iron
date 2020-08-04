package iron.service;

import iron.bean.Blog;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogService {

    void add(Blog blog, MultipartFile[] files) throws IOException;

    void delete(Integer id);

    Blog get(Integer id);

    List<Blog> getLatest(Integer total);

    List<Blog> getAll();

    void edit(Blog blog);
}
