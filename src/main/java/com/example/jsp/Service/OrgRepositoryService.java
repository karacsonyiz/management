package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class OrgRepositoryService {

    private final OrgEntityRepository orgEntityRepository;

    public OrgRepositoryService(OrgEntityRepository orgEntityRepository) {
        this.orgEntityRepository = orgEntityRepository;
    }

    public List<GeneratedOrganizationEntity> listOrgs() {
        return orgEntityRepository.findAll();
    }

}
