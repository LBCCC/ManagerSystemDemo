package com.manager.system.controller;

import com.manager.system.aop.AccessCheck;
import com.manager.system.constant.HeaderConstants;
import com.manager.system.dto.RoleInfo;
import com.manager.system.dto.UserInfo;
import com.manager.system.exception.BusinessException;
import com.manager.system.exception.ErrorCode;
import com.manager.system.util.JsonUtils;
import com.manager.system.util.UserUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class ManagerController {


    @ResponseBody
    @PostMapping("/admin/addUser")
    @AccessCheck(roles = "admin")
    public String addUser(@Valid @RequestBody UserInfo userInfo,
                          BindingResult bindingResult) throws IOException {
        UserUtils.addUser(userInfo);
        return "success";
    }
    @GetMapping("/user/{resource}")
    @ResponseBody
    @AccessCheck(roles = {"user", "admin"})
    public String resource(@PathVariable String resource, @RequestHeader(HeaderConstants.ROLE_INFO) String roleInfo) {
        RoleInfo role = UserUtils.extractAuthInfo(roleInfo);
        if (Objects.isNull(role)) {
            throw new BusinessException(ErrorCode.INVALID_AUTH_INFO);
        }

        UserInfo user = UserUtils.getUser(role.getUserId());
        return Optional.ofNullable(user).map(u -> u.getEndpoint().contains(resource) ? "success" : "failure").orElse("failure");

    }





}
