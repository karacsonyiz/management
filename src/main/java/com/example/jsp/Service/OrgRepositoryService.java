package com.example.jsp.Service;

import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedOrgusersEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.OrgUsersEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Service
public class OrgRepositoryService {

    private OrgEntityRepository orgEntityRepository;
    private UserEntityRepository userEntityRepository;
    private OrgUsersEntityRepository orgUsersEntityRepository;
    @PersistenceContext
    private EntityManager em;

    public OrgRepositoryService(OrgEntityRepository orgEntityRepository, UserEntityRepository userEntityRepository, OrgUsersEntityRepository orgUsersEntityRepository) {
        this.orgEntityRepository = orgEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.orgUsersEntityRepository = orgUsersEntityRepository;
    }

    public void testMtoM(){

        GeneratedOrganizationEntity testOrgEntity = orgEntityRepository.findById(1).get();
        GeneratedOrganizationEntity testOrgEntity2 = orgEntityRepository.findById(2).get();
        GeneratedOrganizationEntity testOrgEntity3 = orgEntityRepository.findById(3).get();

        GeneratedUserEntity testgeneratedUserEntity = userEntityRepository.findById(1).get();
        GeneratedUserEntity testgeneratedUserEntity2 = userEntityRepository.findById(1).get();


        testgeneratedUserEntity2.addOrg(testOrgEntity);
        testgeneratedUserEntity2.addOrg(testOrgEntity3);
    }

    public List<GeneratedOrganizationEntity> listOrgs(){
        return orgEntityRepository.findAll();
    }


    public void testOrgUsersAdd(){

        GeneratedUserEntity user = userEntityRepository.findById(5).get();
        em.merge(new GeneratedOrgusersEntity(user,orgEntityRepository.findById(1).get()));
        em.merge(new GeneratedOrgusersEntity(user, orgEntityRepository.findById(7).get()));
        em.merge(new GeneratedOrgusersEntity(user,orgEntityRepository.findById(3).get()));
    }
}
