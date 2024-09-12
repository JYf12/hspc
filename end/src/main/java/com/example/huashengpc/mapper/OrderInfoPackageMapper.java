package com.example.huashengpc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.huashengpc.models.domain.OrderInfoPackage;
import com.example.huashengpc.models.vo.ExcelDownloadVO;
import com.example.huashengpc.models.vo.OrderPackageListInfoVO;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 70724
 * @description 针对表【order_info_package】的数据库操作Mapper
 * @createDate 2023-04-09 21:39:10
 * @Entity generator.domain.OrderInfoPackage
 */
public interface OrderInfoPackageMapper extends BaseMapper<OrderInfoPackage> {

    @Select("select * from package_info, order_info_package " +
            "where order_info_package.order_id=#{id} and order_info_package.package_id=package_info.id")
    List<OrderPackageListInfoVO> getOrderPackageListInfo(Long id);
    @Select("select order_info_package.order_id, order_info_package.package_id, order_info_package.open_id, " +
            "users.unitname, users.name, users.phone_num, users.email, users.invoice_title, users.tax_number, " +
            "users.bank, users.bank_number, users.bank_phone, users.company_address, users.company_status " +
            "from order_info_package INNER JOIN users on users.open_id = order_info_package.open_id collate Chinese_PRC_90_CI_AS " +
            "where order_info_package.package_id = #{id};")
    List<ExcelDownloadVO> getExcelDownloadList(Long id);
}




