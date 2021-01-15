package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import com.example.jsp.Repository.OrgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgRepositoryService {


    @Autowired
    private OrgEntityRepository orgEntityRepository;
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private OrgRepository orgRepository;

    public void testMtoM(){

        GeneratedOrganizationEntity testOrgEntity = orgEntityRepository.findById(1).get();
        GeneratedOrganizationEntity testOrgEntity2 = orgEntityRepository.findById(2).get();
        GeneratedOrganizationEntity testOrgEntity3 = orgEntityRepository.findById(3).get();

        GeneratedUserEntity testgeneratedUserEntity = userEntityRepository.findById(1).get();
        GeneratedUserEntity testgeneratedUserEntity2 = userEntityRepository.findById(2).get();


        testgeneratedUserEntity2.addOrg(testOrgEntity2);
        testgeneratedUserEntity2.addOrg(testOrgEntity3);
    }

    public List<GeneratedOrganizationEntity> listOrgs(){
        return orgEntityRepository.findAll();
    }
}
