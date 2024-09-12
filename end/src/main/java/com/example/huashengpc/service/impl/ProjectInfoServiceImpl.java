package com.example.huashengpc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huashengpc.mapper.ProjectInfoMapper;
import com.example.huashengpc.models.domain.ProjectInfo;
import com.example.huashengpc.service.ProjectInfoService;
import com.example.huashengpc.utils.Result;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @author 70724
* @description 针对表【project_info】的数据库操作Service实现
* @createDate 2023-03-26 16:50:51
*/
@Service
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoMapper, ProjectInfo>
    implements ProjectInfoService {

    @Resource
    private ProjectInfoMapper projectInfoMapper;

    @Override
    public Map addFile(MultipartFile file) {

        // 文件保存路径
        String savePath = "D:\\PROJECTS\\bidding-agent\\bidding-agent-api\\uploadFiles\\";
//        String savePath = "E:\\华盛项目\\bidding-agent\\end\\src\\main\\resources\\static\\";

        //获取后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //使用UUID重新生成文件名 防重名覆盖
        String newName = UUID.randomUUID().toString()+suffix;
        //文件路径
        String filePath = savePath + newName;

        try {
            //将文件发送到某个绝对路径上
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        String filename = file.getOriginalFilename();

        Map<String, String> map = new HashMap<>();

        map.put("fileName", filename);
        map.put("filePath", newName);

        return map;
    }

    @Override
    public long addProject(String projectName, String projectId, String addition) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", projectId);
        Long count = projectInfoMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }

        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setProjectId(projectId);
        projectInfo.setProjectName(projectName);
        projectInfo.setAddition(addition);
        projectInfo.setStatus(1);
        boolean save = this.save(projectInfo);
        if (!save) {
            return -1;
        }

        return projectInfo.getId();
    }

    @Override
    public String createProjectId() {
        // 获取自定义编号
        // 设置日期格式
        SimpleDateFormat f = new SimpleDateFormat("yyyy");
        String date = f.format(new Date(System.currentTimeMillis()));

        String project_id = "HSZB-"+ date +"-%";
        List<ProjectInfo> list = projectInfoMapper.getProjectList(project_id);

        Integer intNumber = 1;

        if (list.size() < 1) {
            intNumber = 1;
        } else {
            intNumber = list.size() + 1;
        }

        String Number =  String.valueOf(intNumber);

        for (int i = 0; i < 4; i++) {
            Number = Number.length() < 4 ? "0" + Number : Number;
        }

        String projectId = "HSZB-" + date + "-" + Number;

        return projectId;
    }
}




