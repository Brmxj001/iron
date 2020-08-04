package iron.service;

import iron.bean.Blog;
import iron.bean.BlogImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BlogImgService {

    void add(Integer id, MultipartFile file) throws IOException;
    void add(Integer id, MultipartFile[] file) throws IOException;

    void delete(Integer id);

    List<BlogImg> getAllByGid(Integer gid);

    void deleteAllByBig(Integer bid);
}
