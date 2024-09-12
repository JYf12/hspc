package com.example.huashengpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huashengpc.mapper.AdminUserMapper;
import com.example.huashengpc.models.domain.AdminUser;
import com.example.huashengpc.service.AdminUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.huashengpc.constant.AdminUserConstant.SALT;
import static com.example.huashengpc.constant.AdminUserConstant.USER_LOGIN_STATE;

/**
* @author 70724
* @description 针对表【admin_user】的数据库操作Service实现
* @createDate 2023-03-21 12:44:14
*/
@Service
@Slf4j
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser>
    implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public long adminUserRegister(String username, String userAccount, String userPassword, String checkPassword, String isAdmin, String manScope) {
        // 用户名和密码校验

        // 用户名和密码不能为空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }

        // 用户名不能小于4位
        if (userAccount.length() < 4) {
            return -1;
        }

        // 密码不能小于8位
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            return -1;
        }

        // 账户不能包含特殊字符
        String vaildPattren = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(vaildPattren).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        Long count = adminUserMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        if (!isAdmin.equals("0") && !isAdmin.equals("1")) {
            log.error("isAdmin error, not an available value");
            return -1;
        }
//        if (!manScope.equals("0") && !manScope.equals("1")) {
//            log.error("manScope error, not an available value");
//            return -1;
//        }

        // 用户密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 插入数据
        AdminUser adminUser = new AdminUser();
        adminUser.setUsername(username);
        adminUser.setUserAccount(userAccount);
        adminUser.setUserPassword(encryptPassword);
        adminUser.setIsSuperadmin(Integer.valueOf(isAdmin));
//        adminUser.setManScope(Integer.valueOf(manScope));
        boolean save = this.save(adminUser);
        if (!save) {
            return -1;
        }

        return adminUser.getId();
    }

    @Override
    public AdminUser adminUserLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 用户名和密码校验

        // 用户名和密码不能为空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        // 用户名不能小于4位
        if (userAccount.length() < 4) {
            return null;
        }

        // 密码不能小于8位
        if (userPassword.length() < 8) {
            return null;
        }

        // 账户不能包含特殊字符
        String vaildPattren = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(vaildPattren).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        // 用户密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询管理员用户
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);

        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);

        // 用户不存在
        if (adminUser == null) {
            // 日志记录
            log.info("adminUser login failed, userAccount cannot match userPassword!");
            return null;
        }

        // 用户脱敏
        AdminUser safetyAdminUser = getSafetyUser(adminUser);

        // 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyAdminUser);

        return safetyAdminUser;


    }

    @Override
    public AdminUser getSafetyUser(AdminUser originAdminUser) {
        AdminUser safetyAdminUser = new AdminUser();
        safetyAdminUser.setUserAccount(originAdminUser.getUserAccount());
        safetyAdminUser.setUsername(originAdminUser.getUsername());
        safetyAdminUser.setId(originAdminUser.getId());
        safetyAdminUser.setIsSuperadmin(originAdminUser.getIsSuperadmin());
//        safetyAdminUser.setManScope(originAdminUser.getManScope());
        return safetyAdminUser;
    }

    @Override
    public AdminUser getUserInfo(String userAccount, String userPassword) {
        // 用户名和密码校验

        // 用户名和密码不能为空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        // 用户名不能小于4位
        if (userAccount.length() < 4) {
            return null;
        }

        // 密码不能小于8位
        if (userPassword.length() < 8) {
            return null;
        }

        // 账户不能包含特殊字符
        String vaildPattren = "\\pP|\\pS|\\s+";
        Matcher matcher = Pattern.compile(vaildPattren).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }

        // 用户密码加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询管理员用户
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptPassword);

        AdminUser adminUser = adminUserMapper.selectOne(queryWrapper);

        // 用户不存在
        if (adminUser == null) {
            // 日志记录
            log.info("adminUser getInfo failed, userAccount cannot match userPassword!");
            return null;
        }

        // 用户脱敏
        AdminUser safetyAdminUser = getSafetyUser(adminUser);

        return safetyAdminUser;
    }

    @Override
    public boolean isAdminUser(HttpServletRequest request) {
        AdminUser adminUser = (AdminUser) request.getSession().getAttribute(USER_LOGIN_STATE);
        String userAccount = adminUser.getUserAccount();
        System.out.println(adminUser);

        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        Long count = adminUserMapper.selectCount(queryWrapper);
        if (count < 1) {
            return false;
        }
        return true;
    }
}
