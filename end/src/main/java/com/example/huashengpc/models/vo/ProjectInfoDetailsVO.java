package com.example.huashengpc.models.vo;

import com.example.huashengpc.models.domain.PackageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProjectInfoDetailsVO implements Serializable {
    private static final long serialVersionUID = 5375580753493382001L;

    private Long id;

    private String projectName;

    private String projectId;

    private String addition;

    private Integer status;

    private List<PackageInfo> packageList;
}
