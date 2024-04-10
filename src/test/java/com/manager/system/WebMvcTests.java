package com.manager.system;

import com.manager.system.constant.HeaderConstants;
import com.manager.system.controller.ManagerController;
import com.manager.system.dto.UserInfo;
import com.manager.system.util.JsonUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class WebMvcTests {


    @Autowired
    private MockMvc mockMvc;

    private static String adminAuthInfo = Base64.encodeBase64String("{\"userId\": \"admin\", \"accountName\": \"XXX\", \"role\": \"admin\"}".getBytes(StandardCharsets.UTF_8));
    private static String userAuthInfo = Base64.encodeBase64String("{\"userId\": \"user1\", \"accountName\": \"XXX\", \"role\": \"user\"}".getBytes(StandardCharsets.UTF_8));
    private static String userAuthInfo2 = Base64.encodeBase64String("{\"userId\": \"user2\", \"accountName\": \"XXX\", \"role\": \"user\"}".getBytes(StandardCharsets.UTF_8));


    @Test
    public void testAddUser() throws Exception {

        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("user1");
        userInfo.setEndpoint(new HashSet<>(){{add("resourceA");add("resourceB");add("hello");}});
        String bodyJson = JsonUtils.toJson(userInfo);
        assert bodyJson != null;

        mockMvc.perform(post("/admin/addUser")
                        .header(HeaderConstants.ROLE_INFO, adminAuthInfo)
                        .header("Content-Type", "application/json")
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        mockMvc.perform(post("/admin/addUser")
                        .header(HeaderConstants.ROLE_INFO, userAuthInfo)
                        .header("Content-Type", "application/json")
                        .content(bodyJson))
                .andExpect(status().isOk())
                .andExpect(content().string("500001: do not have access"));

    }

    @Test
    public void testResource() throws Exception {

        mockMvc.perform(get("/user/resourceA")
                        .header(HeaderConstants.ROLE_INFO, userAuthInfo))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        mockMvc.perform(get("/user/hello")
                        .header(HeaderConstants.ROLE_INFO, userAuthInfo))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        mockMvc.perform(get("/user/hello")
                        .header(HeaderConstants.ROLE_INFO, userAuthInfo2))
                .andExpect(status().isOk())
                .andExpect(content().string("failure"));

        mockMvc.perform(get("/user/resourceC")
                        .header(HeaderConstants.ROLE_INFO, userAuthInfo))
                .andExpect(status().isOk())
                .andExpect(content().string("failure"));

    }
}
