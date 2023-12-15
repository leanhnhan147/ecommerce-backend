package com.ecommerce.backend.controller;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.service.impl.UserServiceImplement;
import com.ecommerce.backend.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;


public class BasicController {

    @Autowired
    private UserServiceImplement userService;

    public long getCurrentUser(){
        JwtUtils jwtUtils = userService.getAddInfoFromToken();
        return jwtUtils.getAccountId();
    }

    public JwtUtils getSessionFromToken(){
        return userService.getAddInfoFromToken();
    }

    public boolean isAdmin(){
        JwtUtils jwtUtils = userService.getAddInfoFromToken();
        if(jwtUtils !=null){
            return Objects.equals(jwtUtils.getUserKind(), Constant.USER_KIND_ADMIN);
        }
        return false;
    }

    public boolean isManager(){
        JwtUtils jwtUtils = userService.getAddInfoFromToken();
        if(jwtUtils !=null){
            return Objects.equals(jwtUtils.getUserKind(), Constant.USER_KIND_MANAGER);
        }
        return false;
    }

    public boolean isCustomer(){
        JwtUtils jwtUtils = userService.getAddInfoFromToken();
        if(jwtUtils !=null){
            return Objects.equals(jwtUtils.getUserKind(), Constant.USER_KIND_CUSTOMER);
        }
        return false;
    }

}
