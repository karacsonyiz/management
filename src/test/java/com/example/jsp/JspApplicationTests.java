package com.example.jsp;

import com.example.jsp.Controller.ManageController;
import com.example.jsp.Model.User;
import com.example.jsp.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JspApplicationTests {

	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}

	@Test
	void testUsers() {
		List<User> users = userService.listUsers();
		assertThat(users.get(0).getName()).isEqualTo("admin");
		assertThat(users.get(0).getUserid()).isEqualTo(1);
		assertThat(users.get(1).getRole()).isEqualTo("ROLE_USER");

	}

}
