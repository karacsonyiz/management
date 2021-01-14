package com.example.jsp.Dao;

import com.example.jsp.Model.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    public UserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            Long userid = resultSet.getLong("userid");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            String email = resultSet.getString("email");
            String phone = resultSet.getString("phone");
            String address = resultSet.getString("address");
            String role = resultSet.getString("role");
            User user = new User(userid,name,password,email,phone,address,role);
            return user;
        }
    }

    public List<User> listUsers() {
        return jdbcTemplate.query("select userid, name, password, email, phone, address, role from users",new UserMapper());
    }

    public User findUserByUserName(String name){
        try {
            return jdbcTemplate.queryForObject("select userid, name, password, email, phone, address, enabled, role from users where name = ?", new UserMapper(), name);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    public User findUserById(int id){
        try {
            return jdbcTemplate.queryForObject("select userid, name, password, email, phone, address, enabled, role from users where userid = ?", new UserMapper(), id);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    public User findUserByEmail(String email){
        try {
            return jdbcTemplate.queryForObject("select userid, name, password, email, phone, address, enabled, role from users where email = ?", new UserMapper(), email);
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    public void addUser(User user) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into users( name, password, email, phone, address,  role) values(?, ?, ?, ?, ?, ?)");
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            return ps;
        });
    }

    public void updateUser(User user,long userid) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "update users set name=?, password=?, email=?, phone=?, address=?,  role=? where userid = ?");
            ps.setString(1, user.getName());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            ps.setLong(7, userid);
            return ps;
        });
    }

    public Integer countUsers(){
        return jdbcTemplate.queryForObject("select count(userid) from users",Integer.class);
    }

    public void deleteUser(int userid){
        jdbcTemplate.update("delete from users where userid = ?",userid);
    }

    public List<User> listUsersByPage(int offset,int total){
        String totalAsString = String.valueOf(total);
        String offsetString = String.valueOf(offset);
        String queryString = "select * from users limit " + offsetString + " , " + totalAsString;
        return jdbcTemplate.query(queryString,new UserMapper());
    }

}
