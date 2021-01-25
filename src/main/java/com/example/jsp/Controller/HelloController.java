package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrgusersEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.Service.OrgRepositoryService;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@Controller
public class HelloController {

    @PersistenceContext
    private EntityManager em;
    private UserRepositoryService userRepositoryService;
    private OrgRepositoryService orgRepositoryService;

    public HelloController(UserRepositoryService userRepositoryService, OrgRepositoryService orgRepositoryService) {
        this.userRepositoryService = userRepositoryService;
        this.orgRepositoryService = orgRepositoryService;
    }

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

    @RequestMapping(value = "/complexCriteriaSelect", method = RequestMethod.GET)
    public int complexCriteriaSelect() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedUserEntity> userQuery = cb.createQuery(GeneratedUserEntity.class).distinct(true);
        Root<GeneratedUserEntity> userRoot = userQuery.from(GeneratedUserEntity.class);
        Join<GeneratedUserEntity,GeneratedOrgusersEntity> orguser = userRoot.join("orgs");
        orguser.on(cb.equal(cb.substring(userRoot.get("email"),-3),".hu"));
        Join<GeneratedOrgusersEntity,GeneratedOrganizationEntity> org = orguser.join("users");
        org.on(cb.equal(cb.substring(orguser.get("name"),1,1),"k"));
        List<GeneratedUserEntity> result = em.createQuery(userQuery).getResultList();

        return result.size();
    }


}
