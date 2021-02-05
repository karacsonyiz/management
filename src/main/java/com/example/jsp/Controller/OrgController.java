package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.Model.DataTable;
import com.example.jsp.Model.Session;
import com.example.jsp.Service.OrgRepositoryService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class OrgController {

    private final OrgRepositoryService orgRepositoryService;

    public OrgController(OrgRepositoryService orgRepositoryService) {
        this.orgRepositoryService = orgRepositoryService;
    }

    @RequestMapping("/manageorgs")
    public ModelAndView manage(Model model, HttpSession session, HttpServletResponse response)throws IOException {
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        if(sessionBean == null){
            response.sendRedirect("login");
            return null;
        }

        ModelAndView modelAndView = new ModelAndView("manageorgs");
        Object name = session.getAttribute("name");
        model.addAttribute("name", name);
        return modelAndView;
    }

    @RequestMapping(value = "/getOrgs", method = RequestMethod.GET)
    public DataTable getOrgs() {
        List<GeneratedOrganizationEntity> orgEntityList = orgRepositoryService.listOrgs();
        return new DataTable(1, 4, 10, new ArrayList<>(), orgEntityList);
    }

}
