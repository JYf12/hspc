package com.example.huashengpc.models.request;

import com.example.huashengpc.models.vo.TableDataVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AddProjectRequest implements Serializable {
    private static final long serialVersionUID = 7441029182643143715L;

    private String projectName;

    private String addition;

    private List<TableDataVO> tableData;
}
