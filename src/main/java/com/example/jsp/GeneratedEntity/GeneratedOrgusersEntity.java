package com.example.jsp.GeneratedEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orgusers", schema = "demo")
public class GeneratedOrgusersEntity {
    private Integer id;
    private GeneratedUserEntity userByUserid;
    private GeneratedOrganizationEntity organizationByOrgid;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneratedOrgusersEntity that = (GeneratedOrgusersEntity) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "userid", referencedColumnName = "userid", nullable = false)
    public GeneratedUserEntity getUserByUserid() {
        return userByUserid;
    }

    public void setUserByUserid(GeneratedUserEntity userByUserid) {
        this.userByUserid = userByUserid;
    }

    @ManyToOne
    @JoinColumn(name = "organizationid", referencedColumnName = "organizationid", nullable = false)
    public GeneratedOrganizationEntity getOrganizationByOrgid() {
        return organizationByOrgid;
    }

    public void setOrganizationByOrgid(GeneratedOrganizationEntity organizationByOrgid) {
        this.organizationByOrgid = organizationByOrgid;
    }
}
