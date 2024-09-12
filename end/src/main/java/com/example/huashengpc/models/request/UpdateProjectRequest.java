package com.example.huashengpc.models.request;

import com.example.huashengpc.models.vo.PackageInfoVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UpdateProjectRequest implements Serializable {
    private static final long serialVersionUID = 6324498567495025238L;

    private String id;
    private String projectName;
    private String addition;
    private List<PackageInfoVO> packageList;
    private List<Integer> status;
    private Long projectId;

}
