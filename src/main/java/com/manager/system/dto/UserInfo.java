package com.manager.system.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserInfo {

    @NotNull
    private String userId;

    private Set<String> endpoint;

    private String role;


}
