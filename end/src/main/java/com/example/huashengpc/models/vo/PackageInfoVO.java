package com.example.huashengpc.models.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PackageInfoVO implements Serializable {
    private static final long serialVersionUID = 7711368005795221164L;
    private Long id;

    private Long projectId;

    private String packageName;

    private Double price;

    private String filePath;

    private String fileName;

    private String startTime;

    private String endTime;
}
