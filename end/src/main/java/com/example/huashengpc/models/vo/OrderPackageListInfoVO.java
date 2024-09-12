package com.example.huashengpc.models.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

@Data
public class OrderPackageListInfoVO implements Serializable {
    private static final long serialVersionUID = 3501714865381069823L;

    private Long id;

    private String projectId;

    private String packageName;

    private Double price;

    private String filePath;

    private String fileName;

    private Long orderId;

    private Long packageId;

    private String openId;

    private Integer isPay;

    private Integer audit;

}
