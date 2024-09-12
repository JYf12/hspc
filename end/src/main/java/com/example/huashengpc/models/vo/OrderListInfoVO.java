package com.example.huashengpc.models.vo;

import com.example.huashengpc.models.domain.Users;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderListInfoVO implements Serializable {
    private static final long serialVersionUID = -6764147408608464715L;

    private Long id;

    private String projectId;

    private String openId;

    private Integer isToken;

    private Integer status;

    private Integer audit;

    private String addition;

    private Float total;

    private Integer downloadStatus;

    private String name;

    private String mobile;

    private String telephone;

    private String email;

    private String address;

    private String invoiceTitle;

    private String taxNumber;

    private String bank;

    private String bankNumber;

    private String phoneNum;

    private String bankPhone;

    private String companyAddress;

    private String companyStatus;

    private String avatarUrl;

    private String unitName;

}
