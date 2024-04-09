package com.manager.system.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.manager.system.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
public class UserUtils {

    private static final String path = "user.json";

    public synchronized static void addUser(UserInfo userInfo) throws IOException {
        Map<String, UserInfo> map = JsonUtils.fromFile(new File(path), new TypeReference<>() {
        });
        if (map == null) {
            map = new HashMap<>();
        }
        map.put(userInfo.getUserId(), userInfo);
        log.info("修改后的用户表: {}", JsonUtils.toJson(map));
        Path p = Paths.get(path);
        Files.write(p, JsonUtils.toJson(map).getBytes());
        log.info("用户信息已写入: {}", JsonUtils.toJson(userInfo));
    }

    public synchronized static UserInfo getUser(String userId) {
        Map<String, UserInfo> map = JsonUtils.fromFile(new File(path), new TypeReference<>() {
        });
        return Optional.ofNullable(map).map(m -> m.get(userId)).orElse(null);
    }
}
