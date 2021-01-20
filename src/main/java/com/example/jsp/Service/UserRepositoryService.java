package com.example.jsp.Service;

import com.example.jsp.Entity.UserEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrgusersEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntity.LoggerEntity;
import com.example.jsp.GeneratedEntityRepository.LoggerRepository;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.OrgUsersEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.Login;
import com.example.jsp.Repository.UserRepository;
import com.sun.jdi.InvalidStackFrameException;
import liquibase.pro.packaged.D;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.hibernate.exception.spi.SQLExceptionConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.UnexpectedRollbackException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import javax.persistence.EntityManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserRepositoryService {

    private UserRepository userRepository;
    private SqlExceptionHelper sqlExceptionHelper;

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private OrgEntityRepository orgEntityRepository;
    @Autowired
    private OrgUsersEntityRepository orgUsersEntityRepository;
    @Autowired
    private LoggerRepository loggerRepository;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryService.class);


    public List<GeneratedUserEntity> listUsers() {
        return userEntityRepository.findAll();
    }


    //@Transactional(noRollbackFor = { SQLException.class },propagation = Propagation.REQUIRES_NEW)
    public void log(String message){
        loggerRepository.save(new LoggerEntity(message,new Date().toString()));
    }

    public Long findIdByName(String name){
        return userEntityRepository.findIdByName(name);
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

    public Optional<UserEntity> findUserByEmail(String email) {
        UserEntity entity = new UserEntity();
        entity.setEmail(email);
        Example<UserEntity> example = Example.of(entity);
        return userRepository.findOne(example);
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



    public void addUser(GeneratedUserEntity user, Errors errors) {
        validateAddUser(user,errors);
        handleErrors(user, errors);
        if(!errors.hasErrors()){
            LOGGER.info("User with name : " + user.getName() + " has been succesfully saved!");
            log("User with name : " + user.getName() + " has been succesfully saved!");
        }
    }

    @Transactional
    public void handleErrors(GeneratedUserEntity user, Errors errors){
        if(!errors.hasErrors()){
            try {
                userEntityRepository.save(user);
                System.out.println(user.getUserid());
            } catch (DataIntegrityViolationException e){
                String cause = e.getMostSpecificCause().getMessage();
                System.out.println(cause);
                if(cause.contains("key 'email'")){
                    errors.rejectValue("email","This email is not available!","This email is not available!");
                    log("Duplicate entry on email for user: " + user.getName());
                }
                if(cause.contains("key 'name'")){
                    errors.rejectValue("name","This name is not available!","This name is not available!");
                    log("Duplicate entry on name for user: " + user.getName());
                } else {
                    errors.reject(e.getLocalizedMessage(),e.getMostSpecificCause().getMessage());
                    log("Unexpected error happened : " +e.getMostSpecificCause().getMessage());
                }
            }
        }

    }

    public Optional<GeneratedUserEntity> findUserById(int id) {
        return userEntityRepository.findById(id);
    }

    public void deleteUser(int id) {
        Optional<GeneratedUserEntity> user = userEntityRepository.findById(id);
        //Optional<UserEntity> user = findUserById(id);
        user.ifPresent(userEntity -> userEntityRepository.delete(userEntity));
    }

    public long countUsers() {
        return userEntityRepository.count();
    }

    public void generateUsers(){
        String name;
        String password;
        String email;
        String address;
        for(int i =0;i<99998;i++){
            name = "user" + i;
            password = name;
            email = name + "@" + i + name + ".hu";
            address = "Pálya utca " + i;
            GeneratedUserEntity g = new GeneratedUserEntity(name,password,email,"061555555",address,"ROLE_USER",new ArrayList<>());
            //UserEntity u = new UserEntity(name,password,email,"555555",address,"ROLE_USER");
            userEntityRepository.save(g);
        }
    }

    public void addOrgs(List<String> orgs,Integer userid){
        List<GeneratedOrganizationEntity> orgList = new ArrayList<>();
        Optional<GeneratedUserEntity> user = userEntityRepository.findById(userid);

        if(user.isPresent()){
            for(String orgName : orgs){
                GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
                if(org != null && !user.get().getOrgs().contains(org)){
                    em.merge(new GeneratedOrgusersEntity(user.get(),org));
                }
            }
        }
    }

    public void deleteOrgForUser(Integer userid,String orgName){
        Optional<GeneratedUserEntity> user = userEntityRepository.findById(userid);
        GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
        Optional<GeneratedOrgusersEntity> orguser = Optional.empty();

        if(user.isPresent() && org != null) {
            GeneratedOrgusersEntity probeOrgUser;
            probeOrgUser = new GeneratedOrgusersEntity(user.get(), org);
            Example<GeneratedOrgusersEntity> exampleOrgUser = Example.of(probeOrgUser);
            orguser = orgUsersEntityRepository.findOne(exampleOrgUser);
        }

        orguser.ifPresent(generatedOrgusersEntity -> orgUsersEntityRepository.delete(generatedOrgusersEntity));

    }
}
