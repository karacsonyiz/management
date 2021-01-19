package com.example.jsp.Service;
import com.example.jsp.Entity.OrganizationEntity;
import com.example.jsp.Entity.UserEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Model.Login;
import com.example.jsp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRepositoryService {

    private UserRepository userRepository;


    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private OrgEntityRepository orgEntityRepository;


    public List<GeneratedUserEntity> listUsers() {

        List<GeneratedUserEntity> users = userEntityRepository.findAll();
        return users;
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
        handleErrors(user,errors);
    }

    private void handleErrors(GeneratedUserEntity user, Errors errors){
        if(!errors.hasErrors()){
            try{
                userEntityRepository.save(user);
            } catch (DataIntegrityViolationException e){
                String cause = e.getMostSpecificCause().getMessage();
                if(cause.contains("key 'email'")){
                    errors.rejectValue("email","This email is not available!","This email is not available!");
                }
                if(cause.contains("key 'name'")){
                    errors.rejectValue("name","This name is not available!","This name is not available!");
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
        Optional<GeneratedUserEntity> user = findUserById(userid);

        List<GeneratedOrganizationEntity> orgList = new ArrayList<>();



        if(user.isPresent()){
            for(String orgName : orgs){
                GeneratedOrganizationEntity org = orgEntityRepository.findByOrgName(orgName);
                orgList.add(org);
                System.out.println(org.getId());
                System.out.println(org.getName());
            }
            user.get().addMultipleOrgs(orgList);

        }
    }
}
