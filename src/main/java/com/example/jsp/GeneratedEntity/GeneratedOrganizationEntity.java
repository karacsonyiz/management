package com.example.jsp.GeneratedEntity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "organization", schema = "demo")
public class GeneratedOrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organizationid")
    private Integer organizationid;
    @Column(name = "name")
    private String name;
    @JsonIgnoreProperties("orgs")
    @ManyToMany(mappedBy = "orgs")
    @Column(name = "users")
    private List<GeneratedUserEntity> users;

    public GeneratedOrganizationEntity() {
    }

    public GeneratedOrganizationEntity(String name, List<GeneratedUserEntity> users) {
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return organizationid;
    }

    public void setId(Integer organizationid) {
        this.organizationid = organizationid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<GeneratedUserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<GeneratedUserEntity> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedOrganizationEntity that = (GeneratedOrganizationEntity) o;
        return organizationid.equals(that.organizationid) && Objects.equals(name, that.name) && Objects.equals(users, that.users);
    }

    public void addUser(GeneratedUserEntity generatedUserEntity) {
        users.add(generatedUserEntity);
        generatedUserEntity.getOrgs().add(this);
    }

    public void removeUser(GeneratedUserEntity generatedUserEntity) {
        users.remove(generatedUserEntity);
        generatedUserEntity.getOrgs().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationid, name, users);
    }

}
