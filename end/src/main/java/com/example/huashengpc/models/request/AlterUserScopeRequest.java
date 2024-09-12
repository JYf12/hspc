package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlterUserScopeRequest implements Serializable {
    private static final long serialVersionUID = 1225320726410847692L;

    private int id;
    private String manScope;
}
