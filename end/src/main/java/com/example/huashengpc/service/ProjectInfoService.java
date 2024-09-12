package com.example.huashengpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.huashengpc.models.domain.ProjectInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
* @author 70724
* @description 针对表【project_info】的数据库操作Service
* @createDate 2023-03-26 16:50:51
*/
public interface ProjectInfoService extends IService<ProjectInfo> {

    Map addFile(MultipartFile file);

    long addProject(String projectName, String projectId, String addition);

    String createProjectId();
}
