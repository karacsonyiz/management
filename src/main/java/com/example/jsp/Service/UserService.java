package com.example.jsp.Service;

import com.example.jsp.Dao.UserDao;
import com.example.jsp.Model.Login;
import com.example.jsp.Model.User;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> listUsers() {
        return userDao.listUsers();
    }

    public User findUserByUserName(String name) {
        return userDao.findUserByUserName(name);
    }

    public User validateUserForLogin(Login login, Errors errors){
        User user = findUserByUserName(login.getUsername());

        if(user == null){
            errors.rejectValue("username","bad.username","bad username");
            return null;
        }
        return user;
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

    public void validateAddUser(User user,Errors errors){
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
        if(findUserByUserName(user.getName()) != null){
            errors.rejectValue("name","This username already exists!","This username already exists!");
        }
        if(findUserByEmail(user.getEmail()) != null){
            errors.rejectValue("email","This email already exists!","This email already exists!");
        }
        if(!isEmailValid(user.getEmail())){
            errors.rejectValue("email","Invalid email pattern!","Invalid email pattern!");
        }
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public void addUser(User user, Errors errors) {
        validateAddUser(user,errors);
        if(!errors.hasErrors()){
            userDao.addUser(user);
        }
    }

    public void deleteUser(int id) {
        userDao.deleteUser(id);
    }

    public User findUserById(int id) {
        return userDao.findUserById(id);
    }


    public void updateUser(User user, Errors errors) {
        validateAddUser(user,errors);
        if(!errors.hasErrors()){
            userDao.updateUser(user,user.getUserid());
        }
    }

    public List<User> listUsersByPage(int offset, int total) {
        return userDao.listUsersByPage(offset, total);
    }

    public Integer countUsers() {
        return userDao.countUsers();
    }

    public void generateUsers(){
        for(long i =0;i<99997;i++){
            Long id = i+1000;
            String name = "user" + id;
            String email = name + "@" + name + ".hu";
            String address = name + " utca" + id;
            User u = new User(id,name,name,email,"555555",address,"ROLE_USER");
            userDao.addUser(u);
        }
    }
}
