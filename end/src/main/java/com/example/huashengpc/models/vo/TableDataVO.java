package com.example.huashengpc.models.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
public class TableDataVO implements Serializable {
    private static final long serialVersionUID = 6694270598849453200L;

    private String packageName;

    private String price;

    private String filePath;

    private String fileName;

    private String startTime;

    private String endTime;
}
