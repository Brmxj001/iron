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


    @PostMapping("/back/userLogin")
    public String userLogin(Login login, HttpSession session, Model m) {

        Login result = loginDAO.findByUser(login.getUser());
        System.out.println(login.getPassword());
        if (result != null) {
            System.out.println("1");
            if (result.getPassword().equals(login.getPassword())){
                System.out.println("2");
                session.setAttribute("isLogin", true);
                return "redirect:/back/index";
            }
        }
        m.addAttribute("info", "账号或密码错误");
        return "back/login";
    }

    @GetMapping("/back/login")
    public String toLoginPage() {
        return "back/login";
    }
}
