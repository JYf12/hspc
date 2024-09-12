package com.example.huashengpc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.huashengpc.models.domain.ProjectInfo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
* @author 70724
* @description 针对表【project_info】的数据库操作Mapper
* @createDate 2023-03-26 16:50:51
* @Entity generator.domain.ProjectInfo
*/
public interface ProjectInfoMapper extends BaseMapper<ProjectInfo> {

    @Select("select * from project_info where project_id like #{projectId}")
    List<ProjectInfo> getProjectList(String projectId);

}




