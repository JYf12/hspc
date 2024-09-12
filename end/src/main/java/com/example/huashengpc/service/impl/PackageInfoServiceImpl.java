package com.example.huashengpc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huashengpc.mapper.PackageInfoMapper;
import com.example.huashengpc.models.domain.PackageInfo;
import com.example.huashengpc.service.PackageInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 70724
* @description 针对表【package_info】的数据库操作Service实现
* @createDate 2023-04-06 08:26:54
*/
@Service
public class PackageInfoServiceImpl extends ServiceImpl<PackageInfoMapper, PackageInfo>
    implements PackageInfoService {

    @Override
    public long addPackage(String packageName, Double price, String filePath, Long id, String fileName, Date startTime, Date endTime) {
        if (StringUtils.isAnyBlank(packageName, String.valueOf(price), String.valueOf(id), filePath, fileName)) {
            return -1;
        }

        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setPackageName(packageName);
        packageInfo.setPrice(price);
        packageInfo.setProjectId(id);
        packageInfo.setFileName(fileName);
        packageInfo.setFilePath(filePath);
        packageInfo.setStartTime(startTime);
        packageInfo.setEndTime(endTime);
        boolean save = this.save(packageInfo);
        if (!save) {
            return -1;
        }

        return packageInfo.getId();
    }
}




