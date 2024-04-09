package com.manager.system.controller;

import com.manager.system.aop.AccessCheck;
import com.manager.system.dto.UserInfo;
import com.manager.system.util.UserUtils;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("/")
public class ManagerController {


    @ResponseBody
    @PostMapping("/admin/addUser")
    @AccessCheck(endpoint = "addUser", role = "admin")
    public String addUser(@Valid @RequestBody UserInfo userInfo,
                          BindingResult bindingResult) throws IOException {
        userInfo.setRole("user");
        UserUtils.addUser(userInfo);
        return "success";
    }

    @ResponseBody
    @GetMapping("/user/resourceA")
    @AccessCheck(endpoint = "resourceA")
    public String resourceA() {
        return "success";
    }

    @ResponseBody
    @GetMapping("/user/resourceB")
    @AccessCheck(endpoint = "resourceB")
    public String resourceB() {
        return "success";
    }

    @ResponseBody
    @GetMapping("/user/resourceC")
    @AccessCheck(endpoint = "resourceC")
    public String resourceC() {
        return "success";
    }




}
