package com.example.jsp.Controller;

import com.example.jsp.Entity.UserEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.Model.DataTable;
import com.example.jsp.Model.Response;
import com.example.jsp.Model.Session;
import com.example.jsp.Model.User;
import com.example.jsp.Service.UserRepositoryService;
import com.example.jsp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RestController
public class ManageController {

    private UserRepositoryService userRepositoryService;

    public ManageController(UserService userService, UserRepositoryService userRepositoryService) {
        //this.userService = userService;
        this.userRepositoryService = userRepositoryService;
    }

    @GetMapping("/manage")
    public ModelAndView manage(@ModelAttribute("user") GeneratedUserEntity user, Model model, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("manage");
        Object name = session.getAttribute("name");
        model.addAttribute("name", name);
        modelAndView.addObject("userTableStyle","display:none;");
        modelAndView.addObject("successStyle","display:none;");
        return modelAndView;
    }

    @GetMapping("/manageold")
    public ModelAndView manageold(@ModelAttribute("user") User user, Model model, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("manageold");
        Object name = session.getAttribute("name");
        model.addAttribute("name", name);
        return modelAndView;
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public DataTable getUsers(HttpServletRequest request, HttpServletResponse response) throws IOException {

        List<GeneratedUserEntity> userEntityList = userRepositoryService.listUsers();
        long userCount = userRepositoryService.countUsers();
        return new DataTable(1,userCount,10,userEntityList,new ArrayList<>());

    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable String id, HttpServletResponse response) throws IOException {
        userRepositoryService.deleteUser(Integer.parseInt(id));
    }

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public GeneratedUserEntity getUser(@PathVariable String id) {
        return userRepositoryService.findUserById(Integer.parseInt(id)).get();
    }

    @RequestMapping(value = "/updateUser/{id}", method = RequestMethod.POST)
    public void updateUser(@RequestBody User user,@PathVariable String id) {
        //userService.updateUser(user,Integer.parseInt(id));
    }

    @RequestMapping(value = "/countusers", method = RequestMethod.GET)
    public long countusers(){
        return userRepositoryService.countUsers();
        //return userService.countUsers();
    }

    @RequestMapping(value = "/addOrgs/{id}", method = RequestMethod.POST)
    public void addOrgs(@RequestBody Map<String,List<String>> orgs,@PathVariable String id,Errors errors){
        List<String> orgValues = orgs.get("org");

        if(orgValues.size() != 0){
            userRepositoryService.addOrgs(orgValues,Integer.parseInt(id));
        }


        //return userRepositoryService.countUsers();
        //return userService.countUsers();
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("user") GeneratedUserEntity user,HttpServletResponse response, Errors errors) throws IOException {
        ModelAndView modelAndView = new ModelAndView("manage");
        userRepositoryService.addUser(user,errors);
        //checkIfAddOrUpdate(user.getUserid(),user,errors);
        handleErrors(modelAndView,errors,response);
        return modelAndView;
    }

    private void handleErrors(ModelAndView modelAndView,Errors errors,HttpServletResponse response) throws IOException {
        if(errors.hasErrors()){
            modelAndView.addObject("userTableStyle","display:block;");
            modelAndView.addObject("successStyle","display:none;");
        } else {
            modelAndView.addObject("successStyle","display:block;color:green;");
            modelAndView.addObject("userTableStyle","display:none;");
        }
    }

    private void checkIfAddOrUpdate(Long userid,User user,Errors errors){
        if(userid == null){
            //userService.addUser(user,errors);
        } else {
            //userService.updateUser(user,errors);
        }
    }
}
