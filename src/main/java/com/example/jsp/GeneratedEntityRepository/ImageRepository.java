package com.example.jsp.GeneratedEntityRepository;

import com.example.jsp.GeneratedEntity.ImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageEntity, Integer> {

    @Query(value = "SELECT id FROM image", nativeQuery = true)
    List<Integer> getImageIds();
}
