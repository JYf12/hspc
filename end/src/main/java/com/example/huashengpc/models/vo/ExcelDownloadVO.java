package com.example.huashengpc.models.vo;

import lombok.Data;

@Data
public class ExcelDownloadVO {

    private Long orderId;

    private Long packageId;

    private String openId;

    private String unitName;

    private String name;

    private String phoneNum;

    private String email;

    private String invoiceTitle;

    private String taxNumber;

    private String bank;

    private String bankNumber;

    private String bankPhone;

    private String companyAddress;

    private String companyStatus;
}
