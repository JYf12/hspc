package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderListRequest implements Serializable {
    private static final long serialVersionUID = -8235076791895968722L;

    private String username;

    private Long id;
}
