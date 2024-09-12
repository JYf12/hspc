package com.example.huashengpc.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huashengpc.mapper.OrderInfoPackageMapper;
import com.example.huashengpc.mapper.OrderInfoProjectMapper;
import com.example.huashengpc.models.domain.OrderInfoProject;
import com.example.huashengpc.models.domain.PackageInfo;
import com.example.huashengpc.models.vo.OrderListInfoVO;
import com.example.huashengpc.service.OrderInfoProjectService;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 70724
* @description 针对表【order_info_project】的数据库操作Service实现
* @createDate 2023-04-09 16:23:09
*/
@Service
public class OrderInfoProjectServiceImpl extends ServiceImpl<OrderInfoProjectMapper, OrderInfoProject>
    implements OrderInfoProjectService {
    @Resource
    private OrderInfoProjectMapper orderInfoProjectMapper;

    @Override
    public Page<OrderListInfoVO> getOrderList(Integer limit, Integer page, Long id, String username) {
        Page<OrderListInfoVO> orderListInfoVOPage = new Page<>(page, limit);
        username = "%" + username + "%";
        Page<OrderListInfoVO> orderListInfoVOList = orderInfoProjectMapper.selectOrderInfoListPage(orderListInfoVOPage, id, username);

        return orderListInfoVOList;
    }

}




