package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class AlterUserPasRequest implements Serializable {

    private static final long serialVersionUID = 975985283118500496L;

    private int id;
    private String newPassword;
    private String checkNewPas;
}
