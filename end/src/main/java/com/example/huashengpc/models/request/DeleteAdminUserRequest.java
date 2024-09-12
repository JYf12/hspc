package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class DeleteAdminUserRequest implements Serializable {
    private static final long serialVersionUID = 8424044549573767489L;

    private int id;

}
