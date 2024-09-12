package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUserRegisterRequest implements Serializable {


    private static final long serialVersionUID = 7492907798689028388L;
    private String username;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String isAdmin;
    private String manScope;
}
