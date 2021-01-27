package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrgusersEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.OrgUsersEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.Login;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRepositoryService {

    private UserEntityRepository userEntityRepository;
    private EntityManager em;
    private OrgEntityRepository orgEntityRepository;
    private OrgUsersEntityRepository orgUsersEntityRepository;
    private LoggerService loggerService;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryService.class);

    public UserRepositoryService(UserEntityRepository userEntityRepository, EntityManager em, OrgEntityRepository orgEntityRepository, OrgUsersEntityRepository orgUsersEntityRepository, LoggerService loggerService) {
        this.userEntityRepository = userEntityRepository;
        this.em = em;
        this.orgEntityRepository = orgEntityRepository;
        this.orgUsersEntityRepository = orgUsersEntityRepository;
        this.loggerService = loggerService;
    }

    public List<GeneratedUserEntity> listUsers() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedUserEntity> query = cb.createQuery(GeneratedUserEntity.class);
        Root<GeneratedUserEntity> root  =  query.from(GeneratedUserEntity.class);
        return em.createQuery(query).getResultList();
    }

    public Integer findIdByName(String name){
        GeneratedUserEntity entity;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedUserEntity> query = cb.createQuery(GeneratedUserEntity.class);
        Root<GeneratedUserEntity> root  =  query.from(GeneratedUserEntity.class);
        query.where(cb.equal(root.get("name"),name));
        try {
            entity = em.createQuery(query).getSingleResult();
        } catch (NoResultException e){
            loggerService.log("No user found with this name :" + name);
            return null;
        }
        return entity.getUserid();
    }

    public boolean validateUserForLogin(Login login, Errors errors){
        if(findIdByName(login.getUsername()) == null){
            errors.rejectValue("username","Invalid Username!","Invalid Username!");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()){
            return true;
        }
        return false;
    }

    public void validateAddUser(GeneratedUserEntity user,Errors errors){
        if(user.getName().equals("")){
            errors.rejectValue("name","Username can not be empty!","Username can not be empty!");
        }
        if(user.getPassword().equals("")){
            errors.rejectValue("password","Password can not be empty!","Password can not be empty!");
        }
        if(user.getRole() == null){
            errors.rejectValue("role","You must select a role!","You must select a role!");
        }
        if(user.getPhone().length() >12){
            errors.rejectValue("phone","Phone number too long!","Phone number too long!");
        }
        if(!isEmailValid(user.getEmail())){
            errors.rejectValue("email","Invalid email pattern!","Invalid email pattern!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addUser(GeneratedUserEntity user, Errors errors) {
        validateAddUser(user,errors);
        if(!errors.hasErrors()) {
            em.merge(user);
        }
    }

    public void handleErrors(Errors errors,GeneratedUserEntity user,Exception e){
        if(!errors.hasErrors()) {
            String cause = e.getCause().getCause().getMessage();
            if (cause.contains("key 'email'")) {
                errors.rejectValue("email", "This email is not available!", "This email is not available!");
                loggerService.log("Duplicate entry on email for user: " + user.getName());
            }
            if (cause.contains("key 'name'")) {
                errors.rejectValue("name", "This name is not available!", "This name is not available!");
                loggerService.log("Duplicate entry on name for user: " + user.getName());
            } else {
                errors.reject(e.getLocalizedMessage(), e.getCause().getCause().getMessage());
                loggerService.log("Unexpected error happened : " + e.getCause().getCause().getMessage());
            }
        }
    }

    @Transactional
    public GeneratedUserEntity findUserById(int id) {
        return em.find(GeneratedUserEntity.class,id);
    }

    @Transactional
    public void deleteUser(int id) {
        GeneratedUserEntity user = em.find(GeneratedUserEntity.class,id);
        if(user != null){
            em.remove(user);
        }
    }

    public long countUsers() {
        return userEntityRepository.count();
    }

    @Transactional
    public void generateUsers(){
        String name;
        String password;
        String email;
        String address;
        StringBuilder sb = new StringBuilder();
        for(int i =0;i<99998;i++){
            name = "user" + i;
            password = name;
            email = name + "@" + i + name + ".hu";
            address = "Pálya utca " + i;
            GeneratedUserEntity g = new GeneratedUserEntity(name,password,email,"061555555",address,"ROLE_USER",new ArrayList<>());
            em.persist(g);
        }
    }

    @Transactional
    public void addOrgs(List<String> orgs,Integer userid){
        GeneratedUserEntity user = em.find(GeneratedUserEntity.class,userid);

        if(user != null){
            for(String orgName : orgs){
                GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
                if(org != null && !user.getOrgs().contains(org)){
                    em.merge(new GeneratedOrgusersEntity(user,org));
                }
            }
        }
    }

    @Transactional
    public void deleteOrgs(List<String> orgs,Integer userid){
        GeneratedUserEntity user = em.find(GeneratedUserEntity.class,userid);
        if(user != null){
            for(String orgName : orgs){
                GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
                if(org != null){
                    Integer orgusersEntityId = getOrgUsersEntityIdByUserAndOrg(user.getUserid(),org.getId());
                    em.remove(em.find(GeneratedOrgusersEntity.class,orgusersEntityId));
                }
            }
        }
    }

    @Transactional
    public Integer getOrgUsersEntityIdByUserAndOrg(Integer userid,Integer orgid){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedOrgusersEntity> query = cb.createQuery(GeneratedOrgusersEntity.class);
        Root<GeneratedOrgusersEntity> root  =  query.from(GeneratedOrgusersEntity.class);
        Predicate predicateForUserid = cb.equal(root.get("userByUserid"),userid);
        Predicate predicateForOrgid = cb.equal(root.get("organizationByOrgid"),orgid);
        Predicate finalPredicate = cb.and(predicateForUserid,predicateForOrgid);
        query.where(finalPredicate);
        return em.createQuery(query).getResultList().get(0).getId();
    }
}
