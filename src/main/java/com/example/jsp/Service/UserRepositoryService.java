package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrgusersEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.Login;
import com.example.jsp.Model.UserForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRepositoryService {

    private final UserEntityRepository userEntityRepository;
    private final EntityManager em;
    private final OrgEntityRepository orgEntityRepository;
    private final LoggerService loggerService;

    public UserRepositoryService(UserEntityRepository userEntityRepository, EntityManager em, OrgEntityRepository orgEntityRepository, LoggerService loggerService) {
        this.userEntityRepository = userEntityRepository;
        this.em = em;
        this.orgEntityRepository = orgEntityRepository;
        this.loggerService = loggerService;
    }

    public Integer findIdByName(String name) {
        GeneratedUserEntity entity;
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedUserEntity> query = cb.createQuery(GeneratedUserEntity.class);
        Root<GeneratedUserEntity> root = query.from(GeneratedUserEntity.class);
        query.where(cb.equal(root.get("name"), name));
        try {
            entity = em.createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            loggerService.log("No user found with this name :" + name);
            return null;
        }
        return entity.getUserid();
    }

    public boolean validateUserForLogin(Login login, Errors errors) {
        if (findIdByName(login.getUsername()) == null) {
            errors.rejectValue("username", "Invalid Username!", "Invalid Username!");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public void validateAddUser(GeneratedUserEntity user, Errors errors) {
        if (user.getName().equals("")) {
            errors.rejectValue("name", "Username can not be empty!", "Username can not be empty!");
        }
        if (user.getPassword().equals("")) {
            errors.rejectValue("password", "Password can not be empty!", "Password can not be empty!");
        }
        if (user.getRole() == null) {
            errors.rejectValue("role", "You must select a role!", "You must select a role!");
        }
        if (user.getPhone().length() > 12) {
            errors.rejectValue("phone", "Phone number too long!", "Phone number too long!");
        }
        if (!isEmailValid(user.getEmail())) {
            errors.rejectValue("email", "Invalid email pattern!", "Invalid email pattern!");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addUser(GeneratedUserEntity user, Errors errors) {
        validateAddUser(user, errors);
        if (!errors.hasErrors()) {
            em.merge(user);
        }
    }

    /**
     * Sets the rejectvalues to the corresponding exception.
     * If the exception has a root cause, we can set the rejectValue more accurately.
     *
     * @param errors Built-in errors class to handle form errors on frontend.
     * @param e      The exception that is to be handled.
     */
    public void handleErrors(Errors errors, GeneratedUserEntity user, Exception e) {
        if (!errors.hasErrors()) {
            if (e.getCause() != null) {
                String cause = e.getCause().getCause().getMessage();
                if (cause.contains("key 'email'")) {
                    errors.rejectValue("email", "This email is not available!", "This email is not available!");
                    loggerService.log("Duplicate entry on email for user: " + user.getName());
                }
                if (cause.contains("key 'name'")) {
                    errors.rejectValue("name", "This name is not available!", "This name is not available!");
                    loggerService.log("Duplicate entry on name for user: " + user.getName());
                } else {
                    errors.reject(e.getMessage(), e.getCause().getCause().getMessage());
                    loggerService.log("Unexpected error happened : " + e.getCause().getCause().getMessage());
                }
            } else {
                errors.reject(e.getMessage(), e.getMessage());
                loggerService.log("Unexpected error happened : " + e.getMessage());
            }
        }
    }

    @Transactional
    public GeneratedUserEntity findUserById(int id) {
        return em.find(GeneratedUserEntity.class, id);
    }

    @Transactional
    public void deleteUser(int id) {
        GeneratedUserEntity user = em.find(GeneratedUserEntity.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    public long countUsers() {
        return userEntityRepository.count();
    }

    @Transactional
    public void generateUsers() {
        for (int i = 0; i < 99998; i++) {
            em.persist(new GeneratedUserEntity(
                    "user"+i,"user"+i,"user"+i+"@konzorcia.hu",
                    "061555555","Pálya utca "+i,"ROLE_USER",new ArrayList<>()));
        }
    }

    @Transactional
    public void addOrgs(List<String> orgs, Integer userid) {
        GeneratedUserEntity user = em.find(GeneratedUserEntity.class, userid);

        if (user != null) {
            for (String orgName : orgs) {
                GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
                if (org != null && !user.getOrgs().contains(org)) {
                    em.merge(new GeneratedOrgusersEntity(user, org));
                }
            }
        }
    }

    @Transactional
    public void deleteOrgs(List<String> orgs, Integer userid) {
        GeneratedUserEntity user = em.find(GeneratedUserEntity.class, userid);
        if (user != null) {
            for (String orgName : orgs) {
                GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
                if (org != null) {
                    Integer orgusersEntityId = getOrgUsersEntityIdByUserAndOrg(user.getUserid(), org.getId());
                    em.remove(em.find(GeneratedOrgusersEntity.class, orgusersEntityId));
                }
            }
        }
    }

    @Transactional
    public Integer getOrgUsersEntityIdByUserAndOrg(Integer userid, Integer orgid) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedOrgusersEntity> query = cb.createQuery(GeneratedOrgusersEntity.class);
        Root<GeneratedOrgusersEntity> root = query.from(GeneratedOrgusersEntity.class);
        Predicate predicateForUserid = cb.equal(root.get("userByUserid"), userid);
        Predicate predicateForOrgid = cb.equal(root.get("organizationByOrgid"), orgid);
        Predicate finalPredicate = cb.and(predicateForUserid, predicateForOrgid);
        query.where(finalPredicate);
        return em.createQuery(query).getSingleResult().getId();
    }

    /**
     * This method helps to find users in searchOnField method if the given field is an Organization.
     *
     * @param orgName Name of the given organization.
     * @return List of GeneratedUserEntities.
     */
    private List<GeneratedUserEntity> searchOnOrgs(String orgName,int pageNumber,int pageSize) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedOrganizationEntity> query = cb.createQuery(GeneratedOrganizationEntity.class);
        Root<GeneratedOrganizationEntity> root = query.from(GeneratedOrganizationEntity.class);
        query.where(cb.equal(root.get("name"), orgName));
        CriteriaQuery<GeneratedOrganizationEntity> select = query.select(root);
        TypedQuery<GeneratedOrganizationEntity> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(pageNumber);
        typedQuery.setMaxResults(pageSize);
        try {
            return em.createQuery(query).getSingleResult().getUsers();
        } catch (NoResultException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Creates a new GeneratedUserEntity from the submitted form.
     * Handles frontend-based optimistic lock exception.
     *
     * @param userForm Incoming values from the submitted form.
     * @return A GeneratedUserEntity that is matched with the form attributes.
     */
    public GeneratedUserEntity matchFormDataToUserEntity(UserForm userForm) {
        GeneratedUserEntity userEntity = new GeneratedUserEntity();
        if (userForm.getUserid() != null) {
            GeneratedUserEntity foundEntity = findUserById(userForm.getUserid());
            userEntity.setUserid(foundEntity.getUserid());
            userEntity.setVersion(foundEntity.getVersion());
            userEntity.setOrgs(foundEntity.getOrgs());
            userEntity.setEnabled(foundEntity.getEnabled());
        }
        if (userForm.getVersion() != null) {
            if (!userForm.getVersion().equals(userEntity.getVersion())) {
                throw new OptimisticLockException("Version mismatch!", new RuntimeException("Version mismatch!"));
            }
        }
        userEntity.setName(userForm.getName());
        userEntity.setAddress(userForm.getAddress());
        userEntity.setEmail(userForm.getEmail());
        userEntity.setPassword(userForm.getPassword());
        userEntity.setPhone(userForm.getPhone());
        userEntity.setRole(userForm.getRole());
        return userEntity;
    }

    public List<GeneratedUserEntity> getUsersForPageByCriteria(int pageNumber,int pageSize,String field,String value){
        if (field.equals("orgs")) {
            return searchOnOrgs(value,pageNumber,pageSize);
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<GeneratedUserEntity> query = cb.createQuery(GeneratedUserEntity.class);
        Root<GeneratedUserEntity> root = query.from(GeneratedUserEntity.class);
        Predicate predicate = cb.equal(root.get(field), value);
        query.where(predicate);
        CriteriaQuery<GeneratedUserEntity> select = query.select(root);
        TypedQuery<GeneratedUserEntity> typedQuery = em.createQuery(select);
        typedQuery.setFirstResult(pageNumber);
        typedQuery.setMaxResults(pageSize);
        return typedQuery.getResultList();
    }
}
