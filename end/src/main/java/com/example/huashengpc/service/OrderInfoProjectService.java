package com.example.huashengpc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.huashengpc.models.domain.OrderInfoProject;
import com.example.huashengpc.models.domain.PackageInfo;
import com.example.huashengpc.models.vo.OrderListInfoVO;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;

/**
* @author 70724
* @description 针对表【order_info_project】的数据库操作Service
* @createDate 2023-04-09 16:23:09
*/
public interface OrderInfoProjectService extends IService<OrderInfoProject> {

    Page<OrderListInfoVO> getOrderList(Integer limit, Integer page, Long id, String username);

}
