package com.example.jsp.GeneratedEntityRepository;

import com.example.jsp.GeneratedEntity.LanguageEntity;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<LanguageEntity, Integer> {

    @Cacheable("language")
    LanguageEntity findByKeyAndLocale(String key, String locale);

}
