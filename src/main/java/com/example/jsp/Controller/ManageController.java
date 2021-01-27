package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.Model.DataTable;
import com.example.jsp.Model.Session;
import com.example.jsp.Service.LoggerService;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ManageController {

    private UserRepositoryService userRepositoryService;
    private LoggerService loggerService;

    public ManageController(UserRepositoryService userRepositoryService,LoggerService loggerService) {
        this.userRepositoryService = userRepositoryService;
        this.loggerService = loggerService;

    }

    @RequestMapping(value = "/manage", method = RequestMethod.GET)
    public ModelAndView manage(@ModelAttribute("user") GeneratedUserEntity user, Model model, HttpSession httpSession) {
        ModelAndView modelAndView = new ModelAndView("manage");
        Object name = httpSession.getAttribute("name");
        model.addAttribute("name", name);
        modelAndView.addObject("userTableStyle","display:none;");
        return modelAndView;
    }

    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)
    public DataTable getUsers(HttpServletRequest request, HttpServletResponse response){
        List<GeneratedUserEntity> userEntityList = userRepositoryService.listUsers();
        long userCount = userRepositoryService.countUsers();
        return new DataTable(1,userCount,10,userEntityList,new ArrayList<>());
    }

    @RequestMapping(value = "/deleteUser/{id}", method = RequestMethod.GET)
    public void delete(@PathVariable String id, HttpServletResponse response){
        userRepositoryService.deleteUser(Integer.parseInt(id));
    }

    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public GeneratedUserEntity getUser(@PathVariable String id) {
        return userRepositoryService.findUserById(Integer.parseInt(id));
    }

    @RequestMapping(value = "/countusers", method = RequestMethod.GET)
    public long countusers(){
        return userRepositoryService.countUsers();
    }

    @RequestMapping(value = "/addOrgs/{id}", method = RequestMethod.POST)
    public void addOrgs(@RequestBody Map<String,List<String>> orgs,@PathVariable String id,Errors errors){
        List<String> orgValues = orgs.get("org");
        if(orgValues.size() != 0){
            userRepositoryService.addOrgs(orgValues,Integer.parseInt(id));
        }
    }

    @Transactional
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("user") GeneratedUserEntity user,HttpServletResponse response, Errors errors,HttpSession httpSession) throws IOException {
        ModelAndView modelAndView = new ModelAndView("manage");
        if(user.getUserid() != null) {
            user.setOrgs(userRepositoryService.findUserById(user.getUserid()).getOrgs());
        }
        try {
            userRepositoryService.addUser(user, errors);
        } catch (Exception e) {
            userRepositoryService.handleErrors(errors, user,e);
        }
        createErrorMessages(modelAndView, errors);
        if(!errors.hasErrors()){
            Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
            sessionBean.setActionMessage("display:block;color:green;");
            httpSession.setAttribute("sessionBean",sessionBean);
            response.sendRedirect("/manage");
        } else {
            Session sessionBean = (Session) httpSession.getAttribute("sessionBean");
            sessionBean.setActionMessage("display:none;");
            httpSession.setAttribute("sessionBean",sessionBean);
        }
        return modelAndView;
    }

    private void createErrorMessages(ModelAndView modelAndView,Errors errors) {
        if(errors.hasErrors()){
            modelAndView.addObject("userTableStyle","display:block;");
            modelAndView.addObject("successStyle","display:none;");
            modelAndView.addObject("errorMsg", "Error: " +errors.getAllErrors().get(0).getDefaultMessage());
        } else {
            modelAndView.addObject("successStyle","display:block;color:green;");
            modelAndView.addObject("userTableStyle","display:none;");
        }
    }

    @RequestMapping(value = "/deleteOrgForUser/{id}", method = RequestMethod.POST)
    public void deleteOrgForUser(@RequestBody Map<String,List<String>> orgs,@PathVariable String id){
        List<String> orgNames = orgs.get("orgs");
        userRepositoryService.deleteOrgs(orgNames,Integer.parseInt(id));
    }

    @RequestMapping(value = "/resetActionMessage", method = RequestMethod.GET)
    public void resetActionMessage(HttpSession session){
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        sessionBean.setActionMessage("display:none;");
        session.setAttribute("sessionBean",sessionBean);

    }
}
