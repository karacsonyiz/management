package com.example.jsp.Model;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;

import java.util.ArrayList;
import java.util.List;

public class UserForm {

    private Integer userid;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String address;
    private int enabled;
    private String role;
    private Integer version;
    private List<GeneratedOrganizationEntity> orgs = new ArrayList<>();

    public UserForm() {
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<GeneratedOrganizationEntity> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<GeneratedOrganizationEntity> orgs) {
        this.orgs = orgs;
    }
}
