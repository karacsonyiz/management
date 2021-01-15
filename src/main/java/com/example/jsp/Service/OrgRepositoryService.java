package com.example.jsp.Service;

import com.example.jsp.Entity.OrgEntity;
import com.example.jsp.Entity.OrganizationEntity;
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

        GeneratedUserEntity testgeneratedUserEntity = userEntityRepository.findById(1).get();
        GeneratedUserEntity testgeneratedUserEntity2 = userEntityRepository.findById(2).get();


        //System.out.println(testOrgEntity.toString());
        //System.out.println(testgeneratedUserEntity.toString());

        //testgeneratedUserEntity.addOrg(testOrgEntity2);


        for(GeneratedOrganizationEntity o: testgeneratedUserEntity.getOrgs()){
            System.out.println(o.getName());
        }

        //testOrgEntity.addUser(testgeneratedUserEntity2);

        for(GeneratedUserEntity u: testOrgEntity.getUsers()){
            System.out.println(u.getName());
        }





        List<GeneratedOrganizationEntity> orgList = orgEntityRepository.findAll();

        for(GeneratedOrganizationEntity org : orgList){
            //System.out.println(orgList.toString());
        }

        List<GeneratedUserEntity> userList = userEntityRepository.findAll();

        for(GeneratedUserEntity user : userList){
            //System.out.println(user.toString());
        }
    }

    public List<GeneratedOrganizationEntity> listOrgs(){
        return orgEntityRepository.findAll();
    }
}
