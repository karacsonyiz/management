package com.example.jsp.Service;
import com.example.jsp.Entity.UserEntity;
import com.example.jsp.Model.Login;
import com.example.jsp.Repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserRepositoryService {

    private UserRepository userRepository;

    public UserRepositoryService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserEntity> listUsers() {
        return userRepository.findAll();
    }

    public Optional<UserEntity> findUserByUserName(String name){
        UserEntity entity = new UserEntity();
        entity.setName(name);
        Example<UserEntity> example = Example.of(entity);
        return userRepository.findOne(example);
    }

    public UserEntity validateUserForLogin(Login login, Errors errors){
        Optional<UserEntity> entity = findUserByUserName(login.getUsername());

        if(entity.isEmpty()){
            errors.rejectValue("username","Invalid Username!","Invalid Username!");
            return null;
        }
        return entity.get();
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

    public void validateAddUser(UserEntity user,Errors errors){
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

    public void addUser(UserEntity user, Errors errors) {
        validateAddUser(user,errors);
        handleErrors(user,errors);
    }

    private void handleErrors(UserEntity user, Errors errors){
        if(!errors.hasErrors()){
            try{
                userRepository.save(user);
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

    public Optional<UserEntity> findUserById(int id) {
        return userRepository.findById(id);
    }

    public void deleteUser(int id) {
        Optional<UserEntity> user = findUserById(id);
        user.ifPresent(userEntity -> userRepository.delete(userEntity));
    }

    public long countUsers() {
        return userRepository.count();
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
            UserEntity u = new UserEntity(name,password,email,"555555",address,"ROLE_USER");
            userRepository.save(u);
        }
    }

}
