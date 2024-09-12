package com.example.huashengpc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.huashengpc.models.domain.OrderInfoProject;
import com.example.huashengpc.models.vo.OrderListInfoVO;
import org.apache.ibatis.annotations.Select;

/**
* @author 70724
* @description 针对表【order_info_project】的数据库操作Mapper
* @createDate 2023-04-09 16:23:09
* @Entity generator.domain.OrderInfoProject
*/
public interface OrderInfoProjectMapper extends BaseMapper<OrderInfoProject> {

    @Select("select order_info_project.id, order_info_project.project_id, " +
            "order_info_project.open_id, order_info_project.is_token, order_info_project.status, order_info_project.audit, " +
            "order_info_project.addition, order_info_project.total, order_info_project.download_status, users.name, users.mobile, users.telephone, users.email, " +
            "users.address, users.invoice_title, users.tax_number, users.bank, users.bank_number, users.phone_num," +
            "users.bank_phone, users.company_address, users.company_status, users.avatar_url, users.unitname " +
            "from order_info_project INNER JOIN users on order_info_project.open_id = users.open_id " +
            "where order_info_project.project_id = #{id} order by order_info_project.id desc ")
    Page<OrderListInfoVO> selectOrderInfoListPage(Page<OrderListInfoVO> iPage, Long id, String username);

}




