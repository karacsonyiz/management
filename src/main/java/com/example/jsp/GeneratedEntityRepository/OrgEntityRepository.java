package com.example.jsp.GeneratedEntityRepository;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrgEntityRepository extends JpaRepository<GeneratedOrganizationEntity,Integer> {

    @Query(value = "SELECT * FROM ORGANIZATION WHERE name = ?1", nativeQuery = true)
    GeneratedOrganizationEntity findByOrgName(String name);
}
