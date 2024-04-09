package com.manager.system.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.ssl.SslProperties;

import java.io.File;

@Slf4j
public class JsonUtils {



    private static final ObjectMapper objectMapper = new ObjectMapper();


    public static String toJson(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (Exception e) {
            log.error("JsonUtils#toJson error", e);
            return null;
        }
    }

    public static <T> T fromJson(String s, Class<T> type) {
        try {
            return objectMapper.readValue(s, type);
        } catch (Exception e) {
            log.error("JsonUtils#fromJson error", e);
            return null;
        }
    }

    public static <T> T fromJson(String s, TypeReference<T> type) {
        try {
            return objectMapper.readValue(s, type);
        } catch (Exception e) {
            log.error("JsonUtils#fromJson error", e);
            return null;
        }
    }

    public static <T> T fromFile(File file, Class<T> type) {
        try {
            if (!file.exists()) {
                return null;
            }
            return objectMapper.readValue(file, type);
        } catch (Exception e) {
            log.error("JsonUtils#fromFile error", e);
            return null;
        }
    }

    public static <T> T fromFile(File file, TypeReference<T> type) {
        try {
            if (!file.exists()) {
                return null;
            }
            return objectMapper.readValue(file, type);
        } catch (Exception e) {
            log.error("JsonUtils#fromFile error", e);
            return null;
        }
    }

}
