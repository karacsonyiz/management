package com.example.jsp.GeneratedEntityRepository;

import com.example.jsp.GeneratedEntity.LoggerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRES_NEW)
public interface LoggerRepository extends JpaRepository<LoggerEntity,Integer> {

}
