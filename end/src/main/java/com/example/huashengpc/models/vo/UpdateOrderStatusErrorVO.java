package com.example.huashengpc.models.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateOrderStatusErrorVO implements Serializable {

    private static final long serialVersionUID = 4323700167333213856L;
    private int id;
    private String addition;
}
