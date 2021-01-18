package com.example.jsp.GeneratedEntity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "organization", schema = "demo")
public class GeneratedOrganizationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer organizationid;
    private String name;
    @JsonIgnoreProperties("orgs")
    @ManyToMany(mappedBy = "orgs")
    private List<GeneratedUserEntity> users;


    @Column(name = "organizationid")
    public Integer getId() {
        return organizationid;
    }

    public void setId(Integer organizationid) {
        this.organizationid = organizationid;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "users")
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

    public void addUser(GeneratedUserEntity generatedUserEntity){
        users.add(generatedUserEntity);
        generatedUserEntity.getOrgs().add(this);
    }

    public void removeUser(GeneratedUserEntity generatedUserEntity){
        users.remove(generatedUserEntity);
        generatedUserEntity.getOrgs().remove(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationid, name, users);
    }

}
