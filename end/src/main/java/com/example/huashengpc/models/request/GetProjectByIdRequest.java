package com.example.huashengpc.models.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class GetProjectByIdRequest implements Serializable {
    private static final long serialVersionUID = 2798788244899970166L;

    private Long id;
}
