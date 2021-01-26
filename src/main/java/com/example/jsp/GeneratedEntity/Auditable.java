package com.example.jsp.GeneratedEntity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<U> {

    @CreatedBy
    @Column(name = "created_by")
    protected U createdBy;

    @CreatedDate
    @Column(name = "creation_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date creationDate;

    @LastModifiedBy
    @Column(name = "lastupdated_by")
    protected U lastModifiedBy;

    @LastModifiedDate
    @Column(name = "updated_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    protected Date lastModifiedDate;
}
