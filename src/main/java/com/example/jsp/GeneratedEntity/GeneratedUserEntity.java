package com.example.jsp.GeneratedEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
@Where(clause="is_active=1")
public class GeneratedUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Integer userid;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "address")
    private String address;
    @Column(name = "enabled")
    private int enabled = 1;
    @Column(name = "role")
    private String role;
    @Column(name = "is_active")
    private Boolean isactive = true;
    @Version
    private Integer version;
    @Column(name = "orgs")
    @JsonIgnoreProperties("users")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "orgusers", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "organizationid"))
    private List<GeneratedOrganizationEntity> orgs = new ArrayList<>();

    public GeneratedUserEntity() {
    }

    public GeneratedUserEntity(String name, String password, String email, String phone, String address, String role, List<GeneratedOrganizationEntity> orgs) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.role = role;
        this.orgs = orgs;
    }

    public void addOrg(GeneratedOrganizationEntity generatedOrganizationEntity) {
        orgs.add(generatedOrganizationEntity);
        generatedOrganizationEntity.getUsers().add(this);
    }

    public void removeOrg(GeneratedOrganizationEntity generatedOrganizationEntity) {
        orgs.remove(generatedOrganizationEntity);
        generatedOrganizationEntity.getUsers().remove(this);
    }

    public void addMultipleOrgs(List<GeneratedOrganizationEntity> generatedOrganizationEntityList) {
        orgs.addAll(generatedOrganizationEntityList);
        for (GeneratedOrganizationEntity org : generatedOrganizationEntityList) {
            org.getUsers().add(this);
        }
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

    public List<GeneratedOrganizationEntity> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<GeneratedOrganizationEntity> orgs) {
        this.orgs = orgs;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedUserEntity that = (GeneratedUserEntity) o;
        return userid.equals(that.userid) && enabled == that.enabled && Objects.equals(name, that.name) && Objects.equals(password, that.password) && Objects.equals(email, that.email) && Objects.equals(phone, that.phone) && Objects.equals(address, that.address) && Objects.equals(role, that.role) && Objects.equals(orgs, that.orgs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, name, password, email, phone, address, enabled, role, orgs);
    }

    @Override
    public String toString() {
        return "GeneratedUserEntity{" +
                "userid=" + userid +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", enabled=" + enabled +
                ", role='" + role + '\'' +
                ", orgs=" + orgs +
                '}';
    }
}
