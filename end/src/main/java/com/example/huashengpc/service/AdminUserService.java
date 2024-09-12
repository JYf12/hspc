package com.example.huashengpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.huashengpc.models.domain.AdminUser;

import javax.servlet.http.HttpServletRequest;

/**
* @author 70724
* @description 针对表【admin_user】的数据库操作Service
* @createDate 2023-03-21 12:44:14
*/
public interface AdminUserService extends IService<AdminUser> {

    long adminUserRegister(String username, String userAccount, String userPassword, String checkPassword, String isAdmin, String manScope);

    AdminUser adminUserLogin(String userAccount, String userPassword, HttpServletRequest request);

    AdminUser getSafetyUser(AdminUser originAdminUser);

    AdminUser getUserInfo(String userAccount, String userPassword);

    boolean isAdminUser(HttpServletRequest request);
}
