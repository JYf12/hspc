package com.example.huashengpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.huashengpc.models.domain.OrderInfoPackage;
import com.example.huashengpc.models.domain.PackageInfo;
import com.example.huashengpc.models.vo.OrderPackageListInfoVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
* @author 70724
* @description 针对表【order_info_package】的数据库操作Service
* @createDate 2023-04-09 21:39:10
*/
public interface OrderInfoPackageService extends IService<OrderInfoPackage> {

    List<OrderPackageListInfoVO> getOrderPackageList(Long id);


    HSSFWorkbook downloadExcel(List<PackageInfo> packageInfoList);
}
