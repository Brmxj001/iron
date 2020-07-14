package iron.controller;

import iron.bean.contact;
import iron.bean.feedback;
import iron.bean.home;
import iron.dao.ContactDAO;
import iron.dao.FeedbackDAO;
import iron.dao.HomeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangxiaobo
 */
@RestController
public class BackController {

    @Autowired
    FeedbackDAO FeedbackDAO;
    @Autowired
    ContactDAO ContactDAO;
    @Autowired
    HomeDAO homeDAO;

    @PostMapping("/back/feedback/show")
    public feedback showFeedback(@RequestParam(value = "id") Integer id) {
        return FeedbackDAO.findById(id).get();
    }



    @PostMapping("/back/feedback/delete")
    public void showFeedbackDelete(@RequestParam(value = "id") Integer id) {
        FeedbackDAO.deleteById(id);
    }

    @PostMapping("/back/contact/add")
    public void doAddContact(contact c) {
        ContactDAO.save(c);
    }

    @PostMapping("/back/contact/delete")
    public void doDeleteContact(Integer id) {
        ContactDAO.deleteById(id);
    }
}
