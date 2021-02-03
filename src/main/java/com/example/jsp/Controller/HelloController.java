package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrgusersEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.Model.Session;
import com.example.jsp.Service.CacheService;
import com.example.jsp.Service.UserRepositoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;


@RestController
public class HelloController {

    private final EntityManager em;
    private final UserRepositoryService userRepositoryService;
    private final CacheService cacheService;

    public HelloController(UserRepositoryService userRepositoryService, EntityManager em, CacheService cacheService) {
        this.userRepositoryService = userRepositoryService;
        this.em = em;
        this.cacheService = cacheService;
    }

    @GetMapping(value = {"/hello"})
    public ModelAndView hello(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("hello");
        Session sessionBean = (Session) session.getAttribute("sessionBean");
        sessionBean.setActionMessage("display:none;");
        sessionBean.setActionResponse("savesuccess");
        session.setAttribute("sessionBean", sessionBean);
        return modelAndView;
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public void generateUsers() {
        userRepositoryService.generateUsers();
    }

    @RequestMapping(value = "/complexCriteriaSelect", method = RequestMethod.GET)
    public Long complexCriteriaSelect() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<GeneratedUserEntity> userRoot = query.from(GeneratedUserEntity.class);
        Join<GeneratedUserEntity, GeneratedOrgusersEntity> orguser = userRoot.join("orgs");
        orguser.on(cb.equal(cb.substring(userRoot.get("email"), -3), ".hu"));
        Join<GeneratedOrgusersEntity, GeneratedOrganizationEntity> org = orguser.join("users");
        org.on(cb.equal(cb.substring(orguser.get("name"), 1, 1), "k"));
        query.select(cb.countDistinct(userRoot.get("userid")));
        return em.createQuery(query).getSingleResult();
    }

    @RequestMapping(value = "evictCache", method = RequestMethod.GET)
    public void evictCache() {
        cacheService.evictAllCaches();
    }
}
