package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminUserLoginRequest implements Serializable {

    private static final long serialVersionUID = -4038324980161705792L;
    private String userAccount;
    private String userPassword;
}
