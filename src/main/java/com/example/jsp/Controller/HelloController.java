package com.example.jsp.Controller;

import com.example.jsp.Service.UserRepositoryService;
import com.example.jsp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
@Controller
public class HelloController {

    //@Autowired
    //private UserService userService;
    @Autowired
    private UserRepositoryService userRepositoryService;

    @GetMapping("/hello")
    public ModelAndView hello(Model model, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("hello");
        Object sessionBean =  session.getAttribute("sessionBean");
        model.addAttribute("sessionBean",sessionBean);

        return modelAndView;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generateUsers() {
        //userService.generateUsers();
        userRepositoryService.generateUsers();
    }


}
