/*
package com.example.jsp.Entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Table(name = "orgusers")
public class OrgUserKey implements Serializable {

    @Column(name = "userid")
    Integer userid;
    @Column(name = "orgid")
    Integer orgid;

    public OrgUserKey(Integer userid, Integer orgid) {
        this.userid = userid;
        this.orgid = orgid;
    }

    public OrgUserKey() {
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getOrgid() {
        return orgid;
    }

    public void setOrgid(Integer orgid) {
        this.orgid = orgid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgUserKey that = (OrgUserKey) o;
        return Objects.equals(userid, that.userid) && Objects.equals(orgid, that.orgid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userid, orgid);
    }
}
*/