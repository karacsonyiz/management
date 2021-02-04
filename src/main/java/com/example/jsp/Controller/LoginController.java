package com.example.jsp.Controller;

import com.example.jsp.Model.Login;
import com.example.jsp.Model.Session;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    private final UserRepositoryService userRepositoryService;

    public LoginController(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping(value = {"/login", "/"})
    public ModelAndView showLogin() {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("login", new Login());
        return modelAndView;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ModelAndView hello(@ModelAttribute("login") Login login, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView modelAndView = null;
        if (!userRepositoryService.validateUserForLogin(login, result)) {
            modelAndView = new ModelAndView("login");
        } else {
            Session sessionBean = new Session(login);
            request.getSession().setAttribute("sessionBean", sessionBean);
            response.sendRedirect("hello");
        }
        return modelAndView;
    }

}
