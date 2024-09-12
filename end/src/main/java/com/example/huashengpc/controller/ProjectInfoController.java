package com.example.huashengpc.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.huashengpc.mapper.ProjectInfoMapper;
import com.example.huashengpc.models.domain.OrderInfoPackage;
import com.example.huashengpc.models.domain.PackageInfo;
import com.example.huashengpc.models.domain.ProjectInfo;
import com.example.huashengpc.models.request.*;
import com.example.huashengpc.models.vo.PackageInfoVO;
import com.example.huashengpc.models.vo.ProjectInfoDetailsVO;
import com.example.huashengpc.models.vo.TableDataVO;
import com.example.huashengpc.service.OrderInfoPackageService;
import com.example.huashengpc.service.PackageInfoService;
import com.example.huashengpc.service.ProjectInfoService;
import com.example.huashengpc.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/project")
@CrossOrigin
@Slf4j
public class ProjectInfoController {
    @Resource
    ProjectInfoService projectInfoService;

    @Resource
    PackageInfoService packageInfoService;

    @Resource
    ProjectInfoMapper projectInfoMapper;

    @Resource
    OrderInfoPackageService orderInfoPackageService;

    @GetMapping("/allProjectInfo")
    public Result getAllProjectInfo() {
        List<ProjectInfo> list = projectInfoService.list(null);
        System.out.println(list);
        return Result.ok().data(list).code(1);
    }

    @PostMapping("/stopProject/{id}")
    public Result stopProject(@PathVariable Long id) {
        UpdateWrapper<ProjectInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setStatus(2);
        boolean flag = projectInfoService.update(projectInfo, updateWrapper);
        return flag ? Result.ok().code(1) : Result.error().code(1);
    }

    @PostMapping("/startProject/{id}")
    public Result startProject(@PathVariable Long id) {
        UpdateWrapper<ProjectInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.setStatus(1);
        boolean flag = projectInfoService.update(projectInfo, updateWrapper);
        return  flag ? Result.ok().code(1) : Result.error().code(1);
    }

    @PostMapping("/deleteProject/{id}")
    public Result deleteProject(@PathVariable Long id) {
        int flag = projectInfoMapper.deleteById(id);

        if (flag < 0) {
            return Result.error().code(1);
        }

        return Result.ok().code(1);
    }

    @PostMapping("/projectListByLimit/{page}/{limit}")
    public Result getProjectList(@PathVariable Integer page, @PathVariable Integer limit, @RequestBody ProjectListRequest projectListRequest) {

        String projectId = projectListRequest.getProjectId();
        String projectName = projectListRequest.getProjectName();

        if (StringUtils.isAnyBlank(projectId)){
            projectId = "";
        }
        if (StringUtils.isAnyBlank(projectName)){
            projectName = "";
        }
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("project_id", projectId);
        queryWrapper.like("project_name", projectName);
        queryWrapper.orderByDesc("id");
        Page<ProjectInfo> projectInfoPage = projectInfoService.page(new Page<>(page, limit), queryWrapper);
        return Result.ok().data(projectInfoPage).code(1);
    }

    @PostMapping("/addProject")
    public Result addProjecct(@RequestBody AddProjectRequest addProjectRequest) throws ParseException {

        String projectId = projectInfoService.createProjectId();

        String projectName = addProjectRequest.getProjectName();
        String addition = addProjectRequest.getAddition();

        long id = projectInfoService.addProject(projectName, projectId, addition);

        if (id < 0) {
            return Result.error().code(0);
        }

        List<TableDataVO> tableData = addProjectRequest.getTableData();
        for (TableDataVO data: tableData) {

            String packageName = data.getPackageName();
            Double price = Double.valueOf(data.getPrice());
            String filePath = data.getFilePath();
            String fileName = data.getFileName();
            String startTime = data.getStartTime();
            String endTime = data.getEndTime();

            SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date stime = st.parse(startTime);
            Date etime = et.parse(endTime);

            long packageId = packageInfoService.addPackage(packageName, price, filePath, id, fileName, stime, etime);

            if (packageId < 0) {
                return Result.error().code(0);
            }
        }

        return Result.ok().code(1);
    }

    // 查询包件的购买数量
    @PostMapping("/getOrderPackageCount/{id}")
    public Result getOrderPackageCount(@PathVariable long id) {
        QueryWrapper<OrderInfoPackage> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("package_id", id);
        long count = orderInfoPackageService.count(queryWrapper);
        return Result.ok("该包件已被购买，无法删除！").data(count);
    }


    @PostMapping("/updateProject")
    public Result updateProject(@RequestBody UpdateProjectRequest updateProjectRequest) throws ParseException {
        List<PackageInfoVO> packageList = updateProjectRequest.getPackageList();
        List<Integer> status = updateProjectRequest.getStatus();
        Long id = updateProjectRequest.getProjectId();
        String projectName = updateProjectRequest.getProjectName();
        String addition = updateProjectRequest.getAddition();
        String projectId = updateProjectRequest.getId();

        QueryWrapper<PackageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", id);
        List<PackageInfo> packageInfoList = packageInfoService.list(queryWrapper);

//        for (PackageInfo packageInfo : packageInfoList) {
//            boolean flag = false;
//            for (PackageInfoVO packageInfo1 : packageList) {
//                if (packageInfo.getId() == packageInfo1.getId())  {
//                    flag = true;
//                }
//            }
//            if (!flag) {
//                QueryWrapper<OrderInfoPackage> queryWrapper1 = new QueryWrapper<>();
//                queryWrapper1.eq("package_id", packageInfo.getId());
//                if (orderInfoPackageService.count(queryWrapper1)>0) {
//                   log.warn("无法删除包件" + packageInfo.getPackageName() + ", 该包件已被购买。");
//                   continue;
//                }else {
//                    packageInfoService.removeById(packageInfo);
//                    log.info("操作 successfully remove " + packageInfo.getPackageName());
//                }
//            }
//        }

        for (int i = 0; i < packageList.size(); i++) {
            PackageInfo packageInfo = new PackageInfo();
            packageInfo.setPackageName(packageList.get(i).getPackageName());
            packageInfo.setPrice(packageList.get(i).getPrice());
            packageInfo.setFilePath(packageList.get(i).getFilePath());
            packageInfo.setProjectId(packageList.get(i).getProjectId());
            packageInfo.setFileName(packageList.get(i).getFileName());
            packageInfo.setId(packageList.get(i).getId());

            log.info("updateProject 开始时间：" + packageList.get(i).getStartTime());
            log.info("updateProject 结束时间：" + packageList.get(i).getEndTime());

            SimpleDateFormat st = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat et = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date stime = st.parse(packageList.get(i).getStartTime());
            Date etime = et.parse(packageList.get(i).getEndTime());

            packageInfo.setStartTime(stime);
            packageInfo.setEndTime(etime);

            if (status.get(i) == 0) {
                packageInfo.setId(null);
                boolean save = packageInfoService.save(packageInfo);
                if (!save) {
                    log.error("can't save package " + packageInfo.getPackageName() + " , code 1. 无法保存！");
                    return Result.error().code(1);
                }
            }
            if (status.get(i) == 1 || status.get(i) == 2) {
                boolean updateById = packageInfoService.updateById(packageInfo);
                if (!updateById) {
                    log.error("can't save package " + packageInfo.getPackageName() + " , code 2. 无法保存！");
                    return Result.error().code(1);
                }
            }
        }

        QueryWrapper<ProjectInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", id);
        ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper1);

        projectInfo.setProjectName(projectName);
        projectInfo.setAddition(addition);
        projectInfo.setProjectId(projectId);

        boolean update = projectInfoService.updateById(projectInfo);
        if (!update) {
            return Result.error().code(1);
        }

        return Result.ok().code(1);
    }


    @PostMapping("/addProjectFile")
    public Result addProjcetFile(@RequestParam("file") MultipartFile file) throws IOException {
        Map fileMap = projectInfoService.addFile(file);

        if (StringUtils.isAnyBlank(fileMap.get("filePath").toString(), fileMap.get("fileName").toString())) {
            return Result.error().code(0);
        }

        return Result.ok().data(fileMap).code(1);
    }


    @PostMapping("/getProjectById")
    public Result getProjectById(@RequestBody GetProjectByIdRequest getProjectByIdRequest) throws ParseException {
//        String idString = getProjectByIdRequest.getId();
//        if (StringUtils.isAnyBlank(idString)){
//            return Result.ok().code(1).message("No Such Project");
//        }

        Long id = Long.valueOf(getProjectByIdRequest.getId());

        // 项目信息
        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper);
        if (projectInfo == null) {
            log.warn("getProjectById 项目信息为空！");
        }

        // 项目下包件信息
        QueryWrapper<PackageInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("project_id", projectInfo.getId());
        List<PackageInfo> packageInfoList = packageInfoService.list(queryWrapper1);
        if (packageInfoList == null) {
            log.warn("getProjectById 包件信息为空！");
        }

        ProjectInfoDetailsVO projectInfoDetailsVO = new ProjectInfoDetailsVO();
        projectInfoDetailsVO.setId(projectInfo.getId());
        projectInfoDetailsVO.setProjectId(projectInfo.getProjectId());
        projectInfoDetailsVO.setProjectName(projectInfo.getProjectName());
        projectInfoDetailsVO.setAddition(projectInfo.getAddition());
        projectInfoDetailsVO.setStatus(projectInfo.getStatus());
        projectInfoDetailsVO.setPackageList(packageInfoList);

        return Result.ok().data(projectInfoDetailsVO).code(1);
    }
}
