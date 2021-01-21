package com.example.jsp.Controller;

import com.example.jsp.Service.OrgRepositoryService;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;


@RestController
@Controller
public class HelloController {

    @Autowired
    private UserRepositoryService userRepositoryService;
    @Autowired
    private OrgRepositoryService orgRepositoryService;


    @GetMapping("/hello")
    public ModelAndView hello(Model model, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        Object sessionBean =  session.getAttribute("sessionBean");
        model.addAttribute("sessionBean",sessionBean);
        return modelAndView;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generateUsers() {
        userRepositoryService.generateUsers();
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test() {
        orgRepositoryService.testOrgUsersAdd();
    }


}
