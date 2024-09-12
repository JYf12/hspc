package com.example.huashengpc.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.huashengpc.mapper.OrderInfoPackageMapper;
import com.example.huashengpc.models.domain.OrderInfoPackage;
import com.example.huashengpc.models.domain.PackageInfo;
import com.example.huashengpc.models.vo.ExcelDownloadVO;
import com.example.huashengpc.models.vo.OrderPackageListInfoVO;
import com.example.huashengpc.service.OrderInfoPackageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* @author 70724
* @description 针对表【order_info_package】的数据库操作Service实现
* @createDate 2023-04-09 21:39:10
*/
@Service
@Slf4j
public class OrderInfoPackageServiceImpl extends ServiceImpl<OrderInfoPackageMapper, OrderInfoPackage>
    implements OrderInfoPackageService {

    @Resource
    private OrderInfoPackageMapper orderInfoPackageMapper;

    @Override
    public List<OrderPackageListInfoVO> getOrderPackageList(Long id) {
        return orderInfoPackageMapper.getOrderPackageListInfo(id);
    }

    @Override
    public HSSFWorkbook downloadExcel(List<PackageInfo> packageInfoList) {
        String[] excelHeader = {"序号", "投标单位", "联系人", "联系人号码", "电子邮箱", "发票抬头",
                "纳税人识别号", "银行名称", "银行账号", "开户手机号", "公司地址", "企业性质"};
        HSSFWorkbook wb = new HSSFWorkbook();

        for (PackageInfo packageInfo : packageInfoList) {
            HSSFSheet sheet = wb.createSheet(packageInfo.getPackageName());
            HSSFRow row = sheet.createRow((int) 0);
            HSSFCellStyle style = wb.createCellStyle();
            HSSFDataFormat format = wb.createDataFormat();
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            style.setDataFormat(format.getFormat("@"));

            for (int i = 0; i < excelHeader.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellValue(excelHeader[i]);
                cell.setCellStyle(style);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                sheet.autoSizeColumn(i);
                sheet.setColumnWidth(cell.getColumnIndex(), 100 * 50);
            }

            List<ExcelDownloadVO> excelDownloadList = orderInfoPackageMapper.getExcelDownloadList(packageInfo.getId());

            for (int i = 0; i < excelDownloadList.size(); i++) {
                row = sheet.createRow(i + 1);
                ExcelDownloadVO excelDownloadVO = excelDownloadList.get(i);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(excelDownloadVO.getUnitName());
                row.createCell(2).setCellValue(excelDownloadVO.getName());
                row.createCell(3).setCellValue(excelDownloadVO.getPhoneNum());
                row.createCell(4).setCellValue(excelDownloadVO.getEmail());
                row.createCell(5).setCellValue(excelDownloadVO.getInvoiceTitle());
                row.createCell(6).setCellValue(excelDownloadVO.getTaxNumber());
                row.createCell(7).setCellValue(excelDownloadVO.getBank());
                row.createCell(8).setCellValue(excelDownloadVO.getBankNumber());
                row.createCell(9).setCellValue(excelDownloadVO.getBankPhone());
                row.createCell(10).setCellValue(excelDownloadVO.getCompanyAddress());
                row.createCell(11).setCellValue(excelDownloadVO.getCompanyStatus());
            }
        }
        return wb;
    }
}




