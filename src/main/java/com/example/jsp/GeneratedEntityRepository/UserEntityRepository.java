package com.example.jsp.GeneratedEntityRepository;

import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserEntityRepository extends JpaRepository<GeneratedUserEntity, Integer> {

    @Query(value = "SELECT userid FROM USER WHERE name = ?1", nativeQuery = true)
    Long findIdByName(String name);

    @Query(value = "SELECT * FROM USER WHERE is_active = 1 AND name = ?1 AND userid <> ?2", nativeQuery = true)
    List<GeneratedUserEntity> getUsersForExclusionByName(String name, Integer userid);

    @Query(value = "SELECT * FROM USER WHERE is_active = 1 AND email = ?1 AND userid <> ?2", nativeQuery = true)
    List<GeneratedUserEntity> getUsersForExclusionByEmail(String email, Integer userid);

    @Query(value = "SELECT * FROM USER WHERE name = ?1 AND is_active = 1", nativeQuery = true)
    List<GeneratedUserEntity> findByName(String name);

    @Query(value = "SELECT * FROM USER WHERE email = ?1 AND is_active = 1", nativeQuery = true)
    List<GeneratedUserEntity> findByEmail(String email);

    @Query(value = "SELECT theme FROM USER WHERE name = ?1 AND is_active = 1", nativeQuery = true)
    String getUserThemeByName(String name);

}
