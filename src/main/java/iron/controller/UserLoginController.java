package iron.controller;

import iron.bean.Login;
import iron.dao.LoginDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @author wangxiaobo
 */
@Controller
public class UserLoginController {

    private final LoginDAO loginDAO;

    @Autowired
    public UserLoginController(LoginDAO loginDAO) {
        this.loginDAO = loginDAO;
    }

    @ResponseBody
    @PostMapping("/back/checkUser")
    public String doCheckUser(String user) {
        Login login = loginDAO.findByUser(user);
        if (null == login) {
            return "fail";
        }
        return "success";
    }

    @ResponseBody
    @PostMapping("/back/uploadUser")
    public void doUploadUser(String oldUser, String newUser, String oldPassword, String newPassword) {
        Login user = loginDAO.findByUser(oldUser);
        if (null != user){
            if (user.getPassword().equals(oldPassword)){
                user.setUser(newUser);
                user.setPassword(newPassword);
                loginDAO.save(user);
            }
        }
    }

    @PostMapping("/iron/back/userLogin")
    public String userLogin(Login login, HttpSession session, Model m) {

        Login result = loginDAO.findByUser(login.getUser());
        if (result != null) {
            if (result.getPassword().equals(login.getPassword())) {
                session.setAttribute("isLogin", true);
                return "redirect:/iron/back/index";
            }
        }
        m.addAttribute("info", "账号或密码错误");
        return "back/login";
    }

    @GetMapping("/iron/back/login")
    public String toLoginPage() {
        return "back/login";
    }
}
