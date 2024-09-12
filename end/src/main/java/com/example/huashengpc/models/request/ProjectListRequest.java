package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProjectListRequest implements Serializable {
    private static final long serialVersionUID = 2147395922902594003L;
    private String projectId;
    private String projectName;
}
