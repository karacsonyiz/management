package com.example.jsp.GeneratedEntityRepository;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<GeneratedUserEntity, Integer> {

    @Query(value = "SELECT userid FROM USER WHERE name = ?1", nativeQuery = true)
    Long findIdByName(String name);

    List<GeneratedUserEntity> findByName(String name);

}
