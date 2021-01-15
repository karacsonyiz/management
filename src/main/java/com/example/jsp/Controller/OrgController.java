package com.example.jsp.Controller;

import com.example.jsp.Entity.OrgEntity;
import com.example.jsp.Entity.UserEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.Model.DataTable;
import com.example.jsp.Model.User;
import com.example.jsp.Service.OrgRepositoryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RestController
public class OrgController {

    private OrgRepositoryService orgRepositoryService;

    public OrgController(OrgRepositoryService orgRepositoryService) {
        this.orgRepositoryService = orgRepositoryService;
    }

    @GetMapping("/manageorgs")
    public ModelAndView manage(@ModelAttribute("user") User user, Model model, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("manageorgs");
        Object name = session.getAttribute("name");
        model.addAttribute("name", name);
        return modelAndView;
    }

    @RequestMapping(value = "/getOrgs", method = RequestMethod.GET)
    public DataTable getOrgs() {
        List<GeneratedOrganizationEntity> orgEntityList =  orgRepositoryService.listOrgs();

        for(GeneratedOrganizationEntity o : orgEntityList){
            System.out.println(o.getName());
        }

        return new DataTable(1,4,10,new ArrayList<>(),orgEntityList,new HashMap<>());
    }

}
