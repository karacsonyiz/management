package com.example.jsp;

import com.example.jsp.Controller.HelloController;
import com.example.jsp.GeneratedEntity.GeneratedOrganizationEntity;
import com.example.jsp.GeneratedEntity.GeneratedUserEntity;
import com.example.jsp.GeneratedEntityRepository.OrgEntityRepository;
import com.example.jsp.GeneratedEntityRepository.UserEntityRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JspApplicationTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @MockBean
    private HelloController helloController;

    @MockBean
    private OrgEntityRepository orgEntityRepository;

    @Test
    public void contextLoads() {
        assertThat(entityManager).isNotNull();
        assertThat(userEntityRepository).isNotNull();
        assertThat(helloController).isNotNull();
        assertThat(orgEntityRepository).isNotNull();
    }

    @Before
    public void setUp() {
        GeneratedOrganizationEntity org = new GeneratedOrganizationEntity("konzorcia", new ArrayList<>());
        Mockito.when(orgEntityRepository.findByOrgName(org.getName())).thenReturn(org);
    }

    @Test
    public void testSetupWithMockito() {
        String orgName = "konzorcia";
        GeneratedOrganizationEntity foundOrg = orgEntityRepository.findByOrgName(orgName);
        assertThat(foundOrg.getName()).isEqualTo(orgName);
    }

    @Test
    public void testPersistUserThenGetUserName() {
        GeneratedUserEntity user = new GeneratedUserEntity("testuser", "password", "user@user.com", "061555444", "Malom utca 5.", "ROLE_USER", new ArrayList<>());
        entityManager.persist(user);
        entityManager.flush();
        GeneratedUserEntity foundUser = userEntityRepository.findByName(user.getName()).get(0);
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }
}
