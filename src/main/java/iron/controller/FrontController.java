package iron.controller;

import iron.bean.feedback;
import iron.dao.FeedbackDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FrontController {
    @Autowired
    FeedbackDAO FeedbackDAO;

    @PostMapping("/front/feedback/submit")
    public void doFeedBackSubmit(feedback feedback) {
        FeedbackDAO.save(feedback);
    }


    @PostMapping("/front/changeLanguage")
    public void doChangeLanguage(HttpServletRequest req, HttpServletResponse res){
        String language = (String) req.getSession().getAttribute("i_language");
        System.out.println(language);
        String l = "zh";
        if (language.equals(l)) {
            res.addCookie(new Cookie("i_language","en"));
            System.out.println("等于zh");
        } else {
            res.addCookie(new Cookie("i_language","zh"));
        }
    }
}
