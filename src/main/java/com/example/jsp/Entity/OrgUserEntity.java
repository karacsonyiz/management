/*
package com.example.jsp.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "orgusers")
public class OrgUserEntity {

    @EmbeddedId
    OrgUserKey id;

    @ManyToOne
    @MapsId("userid")
    @JoinColumn(name = "userid")
    UserEntity userEntity;

    @ManyToOne
    @MapsId("id")
    @JoinColumn(name = "orgid")
    OrgEntity orgEntity;

    public OrgUserEntity(OrgUserKey id, UserEntity userEntity, OrgEntity orgEntity) {
        this.id = id;
        this.userEntity = userEntity;
        this.orgEntity = orgEntity;
    }

    public OrgUserKey getId() {
        return id;
    }

    public void setId(OrgUserKey id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public OrgEntity getOrgEntity() {
        return orgEntity;
    }

    public void setOrgEntity(OrgEntity orgEntity) {
        this.orgEntity = orgEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgUserEntity that = (OrgUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(userEntity, that.userEntity) && Objects.equals(orgEntity, that.orgEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userEntity, orgEntity);
    }
}
*/