package com.example.jsp.Controller;

import com.example.jsp.Entity.UserEntity;
import com.example.jsp.Model.Login;
import com.example.jsp.Model.Session;
import com.example.jsp.Service.UserRepositoryService;
import com.example.jsp.Service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class LoginController {

    //private UserService userService;
    private UserRepositoryService userRepositoryService;

    public LoginController(UserService userService, UserRepositoryService userRepositoryService) {
        //this.userService = userService;
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping("/login")
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("login", new Login());
        return modelAndView;
    }

    @RequestMapping(value = "/hello", method = RequestMethod.POST)
    public ModelAndView hello(@ModelAttribute("login") Login login, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws IOException {

        Session sessionBean = new Session(new Login(login.getUsername(), login.getPassword()));
        ModelAndView modelAndView = null;
        UserEntity user = userRepositoryService.validateUserForLogin(login, result);

        if(user == null){
            modelAndView = new ModelAndView("login");
        } else {
            request.getSession().setAttribute("sessionBean",sessionBean);
            response.sendRedirect("hello");
        }

        return modelAndView;
    }







}
