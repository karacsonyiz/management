package com.example.jsp.Model;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class UserForm {

    private Integer userid;

    @Size(min = 1, max = 50)
    private String name;
    @Size(min = 1, max = 50)
    private String password;
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;
    @Size(max=12)
    private String phone;
    private String address;
    private Integer enabled;
    @NotNull
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

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
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

    @Override
    public String toString() {
        return "UserForm{" +
                "userid=" + userid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", enabled=" + enabled +
                ", role='" + role + '\'' +
                ", version=" + version +
                ", orgs=" + orgs +
                '}';
    }
}
