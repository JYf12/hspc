package com.example.huashengpc.controller;

import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.huashengpc.models.domain.*;
import com.example.huashengpc.models.request.*;
import com.example.huashengpc.service.*;
import com.example.huashengpc.utils.JWTUtil;
import com.example.huashengpc.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.huashengpc.constant.AdminUserConstant.SALT;

@RestController
@RequestMapping("/adminuser")
@CrossOrigin
@Slf4j
public class AdminUserController {

    @Resource
    private AdminUserService adminUserService;

    @Resource
    private OrderInfoProjectService orderInfoProjectService;

    @Resource
    private ProjectInfoService projectInfoService;

    @Resource
    private OrderInfoPackageService orderInfoPackageService;

    @Resource
    private UsersService usersService;

    @PostMapping("/register")
    public Result adminUserRegister(@RequestBody AdminUserRegisterRequest adminUserRegisterRequest) {
        if (adminUserRegisterRequest == null) {
            return Result.error().code(1);
        }
        String userAccount = adminUserRegisterRequest.getUserAccount();
        String userPassword = adminUserRegisterRequest.getUserPassword();
        String checkPassword = adminUserRegisterRequest.getCheckPassword();
        String username = adminUserRegisterRequest.getUsername();
        String isAdmin = adminUserRegisterRequest.getIsAdmin();
        String manScope = adminUserRegisterRequest.getManScope();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, isAdmin)) {
            return Result.error().code(1);
        }
        long register = adminUserService.adminUserRegister(username, userAccount, userPassword, checkPassword, isAdmin, manScope);
        if (register < 0) {
            return Result.error().code(1);
        }
        return Result.ok().code(1);
    }


    @PostMapping("/login")
    public Result adminUserLogin(@RequestBody AdminUserLoginRequest adminUserLoginRequest, HttpServletRequest request) {
        if (adminUserLoginRequest == null) {
            return Result.error().code(0);
        }
        String userAccount = adminUserLoginRequest.getUserAccount();
        String userPassword = adminUserLoginRequest.getUserPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return Result.error().code(0);
        }
        AdminUser adminUser = adminUserService.adminUserLogin(userAccount, userPassword, request);
        if (adminUser == null) {
            return Result.error().code(0);
        }
        Map<String, String> map = new HashMap<>();
        map.put("userAccount", adminUser.getUserAccount());
        map.put("username", adminUser.getUsername());
        map.put("isSuperAdmin", adminUser.getIsSuperadmin().toString());
        String token = JWTUtil.getToken(map);
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("token", token);
        return Result.ok().data(resultMap).code(1);
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */

    @GetMapping("/info")
    public Result adminUserInfo(HttpServletRequest request) {
        String token = request.getParameter("token");
        DecodedJWT verify = JWTUtil.verify(token);
        Map<String, Claim> claims = verify.getClaims();
        String isSuperAdmin = claims.get("isSuperAdmin").asString();
        String username = claims.get("username").asString();
        String role = "";
        if (isSuperAdmin.equals("0")) {
            role = "[editor]";
        }else {
            role = "[admin]";
        }

        QueryWrapper<OrderInfoProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        List<OrderInfoProject> orderInfoProjects = orderInfoProjectService.list(queryWrapper);

        List<Map> orders = new ArrayList<>();
        for (OrderInfoProject orderInfoProject : orderInfoProjects) {
            Map<String, Object> orderInfo = new HashMap<>();

            QueryWrapper<ProjectInfo> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("id", orderInfoProject.getProjectId());
            ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper1);

            QueryWrapper<OrderInfoPackage> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("order_id", orderInfoProject.getId());
            List<OrderInfoPackage> orderInfoPackageList = orderInfoPackageService.list(queryWrapper2);

            Users users = new Users();
            users.setUnitname("");
            QueryWrapper<Users> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("open_id", orderInfoProject.getOpenId());
            users = usersService.getOne(queryWrapper3);

            orderInfo.put("status", orderInfoProject.getStatus());
            orderInfo.put("projectName", projectInfo.getProjectName());
            orderInfo.put("projectId", projectInfo.getProjectId());
            orderInfo.put("id", orderInfoProject.getId());
            orderInfo.put("count", orderInfoPackageList.size());
            String unitName = users.getUnitname();
            if (StringUtils.isAnyBlank(unitName)){
                unitName = "";
            }
            orderInfo.put("company", unitName);

            orders.add(orderInfo);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("role", role);
        map.put("name", username);
        map.put("avatar", "https://oss.aliyuncs.com/aliyun_id_photo_bucket/default_handsome.jpg");
        map.put("order", orders);
        return Result.ok().data(map).code(1);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.ok().code(1);
    }

    @PostMapping("/deleteAdminUser")
    public Result deleteAdminUser(@RequestBody DeleteAdminUserRequest deleteAdminUserRequest) {
        int id = deleteAdminUserRequest.getId(); // 获取需要删除用户id

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        boolean remove = adminUserService.remove(queryWrapper); // 删除该id用户
        if (!remove) {
            log.error("deleteAdminUser error: remove failed!");
            Result.error().code(1); // 删除失败
        }
        return Result.ok().code(1);
    }

    @PostMapping("/alterUserScope")
    public Result alterUserScope(@RequestBody AlterUserScopeRequest alterUserScopeRequest) {
        int id = alterUserScopeRequest.getId();
        String manScope = alterUserScopeRequest.getManScope();
        if (StringUtils.isAnyBlank(manScope)) {
            log.error("alterUserScope error: manScope is null!");
            return Result.error().code(1);
        }
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        AdminUser adminUser = adminUserService.getOne(queryWrapper);
//        adminUser.setManScope(Integer.valueOf(manScope));
        boolean updateById = adminUserService.updateById(adminUser);
        if (!updateById) {
            log.error("alterUserScope error: updateById failed!");
            return Result.error().code(1);
        }

        return Result.ok().code(1);
    }

    @PostMapping("/alterPassword")
    public Result alterPassword(@RequestBody AlterUserPasRequest alterUserPasRequest) {
        int id = alterUserPasRequest.getId();
        String newPassword = alterUserPasRequest.getNewPassword();
        String checkNewPas = alterUserPasRequest.getCheckNewPas();

        if (StringUtils.isAnyBlank(newPassword, checkNewPas)) {
            log.error("alterPassword error: newPassword and checkNewPas is null!");
            return Result.error().code(1);
        }

        if (!newPassword.equals(checkNewPas)) {
            log.error("alterPassword error: checkNewPas does not equal newPassword.");
            return Result.error().code(1);
        }

        if (newPassword.length() < 8) {
            log.error("alterPassword error: length of password < 8");
            return Result.error().code(1);
        }

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        AdminUser adminuser = adminUserService.getOne(queryWrapper);

        // 用户密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + newPassword).getBytes());

        adminuser.setUserPassword(encryptPassword);
        boolean updateById = adminUserService.updateById(adminuser);
        if (!updateById) {
            log.error("alterPassword error: updateById failed!");
            return Result.error().code(1);
        }

        return Result.ok().code(1);
    }

    @GetMapping("/userList")
    public Result getUserList(HttpServletRequest request) {
//        System.out.println(request.getSession());
        List<AdminUser> list = adminUserService.list(null);
        List<AdminUser> safeUsers = new ArrayList<>();
        for (AdminUser user: list){
            AdminUser safetyUser = adminUserService.getSafetyUser(user);
            safeUsers.add(safetyUser);
        }
        return Result.ok().data(safeUsers).code(1);
    }
}
