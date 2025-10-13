package com.example.danew_spring;

import com.example.danew_spring.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class DanewSpringApplicationTests {

	@Autowired
	private AuthService authService;

//	@Test
//	public void addUserTest() {
//		User user = new User();
//		user.setUserId("0626");
//		user.setPassword("123456");
//		user.setName("강민주");
//		user.setAge(24);
//		user.setGender("여성");
//		user.setCreatedAt(LocalDateTime.now().toString());
//		user.setKeywordList(List.of(new String[]{"정치", "IT", "AI"}));
//		user.setCustomList(List.of(new String[]{}));
//
//		authService.save(user);
//	}

}
