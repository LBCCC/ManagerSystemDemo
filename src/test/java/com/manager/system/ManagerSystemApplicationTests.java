package com.manager.system;

import com.manager.system.dto.UserInfo;
import com.manager.system.util.UserUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.HashSet;

@SpringBootTest
@Slf4j
class ManagerSystemApplicationTests {


	@Test
	void contextLoads() {
		log.info("test log");
	}

	@Test
	public void testAddUser() throws IOException {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserId("admin");
		userInfo.setEndpoint(new HashSet<>(){{add("addUser");}});
		UserUtils.addUser(userInfo);
	}

}
