package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.LoggerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Transactional(propagation = Propagation.REQUIRES_NEW)
@Service
public class LoggerService {

    private final EntityManager em;
    public static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryService.class);


    public LoggerService(EntityManager em) {
        this.em = em;
    }

    public void log(String message) {
        em.persist(new LoggerEntity(message));
    }
}
