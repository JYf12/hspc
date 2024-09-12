package com.example.huashengpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.huashengpc.models.domain.PackageInfo;

import java.util.Date;

/**
* @author 70724
* @description 针对表【package_info】的数据库操作Service
* @createDate 2023-04-06 08:26:54
*/
public interface PackageInfoService extends IService<PackageInfo> {

    long addPackage(String packageName, Double price, String filePath, Long id, String fileName, Date startTime, Date endTime);

}
