package com.example.huashengpc.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.huashengpc.models.domain.*;
import com.example.huashengpc.models.request.OrderListRequest;
import com.example.huashengpc.models.vo.OrderListInfoVO;
import com.example.huashengpc.models.vo.OrderPackageListInfoVO;
import com.example.huashengpc.models.vo.UpdateOrderStatusErrorVO;
import com.example.huashengpc.service.*;
import com.example.huashengpc.utils.CreateJson;
import com.example.huashengpc.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.example.huashengpc.constant.OrderConstant.IMGURL;

@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/order")
public class OrderInfoController {

    @Resource
    OrderInfoProjectService orderInfoProjectService;

    @Resource
    OrderInfoPackageService orderInfoPackageService;

    @Resource
    ProjectInfoService projectInfoService;

    @Resource
    UsersService usersService;

    @Resource
    FileAdditionService fileAdditionService;

    @Resource
    UsersAuditService usersAuditService;

    @Resource
    FileAdditionAuditService fileAdditionAuditService;

    @Resource
    PackageInfoService packageInfoService;

    @PostMapping("/getOrderList/{page}/{limit}")
    public Result getOrderList(@PathVariable Integer limit, @PathVariable Integer page, @RequestBody OrderListRequest orderListRequest){

        String username = orderListRequest.getUsername();
//        String idString = orderListRequest.getId();
//        if (StringUtils.isAnyBlank(idString)) {
//            idString = "";
//        }
        if (StringUtils.isAnyBlank(username)) {
            username = "";
        }

        Long id = Long.valueOf(orderListRequest.getId());

        QueryWrapper<ProjectInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper);
        if (projectInfo == null) {
            log.error("getOrderList 没有编号为 " + id.toString()+ " 的项目！");
        }

        Page<OrderListInfoVO> orderList = orderInfoProjectService.getOrderList(limit, page, projectInfo.getId(), username);

        return Result.ok().data(orderList).code(1);
    }

    @PostMapping("/getOrderById/{id}")
    public Result getOrderById(@PathVariable Long id) {

        QueryWrapper<OrderInfoProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        OrderInfoProject orderInfoProject = orderInfoProjectService.getOne(queryWrapper);
        if (orderInfoProject == null) {
            log.warn("getOrderById 订单信息为空！");
        }
        if (StringUtils.isAnyBlank(orderInfoProject.getAddition())) {
            orderInfoProject.setAddition("");
        }
        if (StringUtils.isAnyBlank(orderInfoProject.getOrderPicture())) {
            orderInfoProject.setOrderPicture("");
        }
        orderInfoProject.setOrderPicture(IMGURL + orderInfoProject.getOrderPicture());

       /* QueryWrapper<Users> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("open_id", orderInfoPackage.getOpenId());
        Users users = usersService.getOne(queryWrapper1);
        if (users == null) {
            log.warn("getOrderById 用户信息为空！");
        }
        if (StringUtils.isAnyBlank(users.getPhoneNum())) {
            users.setPhoneNum("");
        }*/

        QueryWrapper<OrderInfoPackage> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("order_id", orderInfoProject.getId());
//        OrderInfoPackage orderInfoPackage = orderInfoPackageService.getOne(queryWrapper1);
        OrderInfoPackage op = null;     //记录投标人信息
        List<OrderInfoPackage> orderInfoPackages = orderInfoPackageService.list(queryWrapper1);
        for(OrderInfoPackage orderInfoPackage : orderInfoPackages){
            if(op==null){
                op = orderInfoPackage;
            }
            if (orderInfoPackage == null) {
            log.warn("getOrderById 用户信息为空！");
        }
        if (StringUtils.isAnyBlank(orderInfoPackage.getPhoneNum())) {
            orderInfoPackage.setPhoneNum("");
        }
        }
//        if (orderInfoPackage == null) {
//            log.warn("getOrderById 用户信息为空！");
//        }
//        if (StringUtils.isAnyBlank(orderInfoPackage.getPhoneNum())) {
//            orderInfoPackage.setPhoneNum("");
//        }
//
        QueryWrapper<FileAddition> queryWrapper2 = new QueryWrapper<>();
        queryWrapper2.eq("open_id", orderInfoProject.getOpenId());
        List<FileAddition> fileAdditionList = fileAdditionService.list(queryWrapper2);
        if (fileAdditionList == null) {
            log.warn("getOrderById 用户材料信息为空！");
        }

        Map<String, Object> fileAdditionMap = new HashMap<>();
        int[] flag = {0, 0, 0, 0};
        String[] flagName = {"ID0", "ID1", "Auth", "Licen"};
        for (FileAddition fileAddition : fileAdditionList) {
            fileAddition.setFilePath(IMGURL + fileAddition.getFilePath());
            String name = fileAddition.getName();
            fileAdditionMap.put(name, fileAddition);
            // 判断查询到的数据中是否有该项数据
            if (name.equals("ID0")) {
                flag[0] = 1;
            } else if (name.equals("ID1")) {
                flag[1] = 1;
            } else if (name.equals("Auth")) {
                flag[2] = 1;
            } else if (name.equals("Licen")) {
                flag[3] = 1;
            }
        }
        // 对于不存在的信息新建一条虚拟记录占位
        for (int i = 0; i < 4; i++) {
            if (flag[i] == 0) {
                FileAddition newFileAddition = new FileAddition();
                newFileAddition.setName(flagName[i]);
                newFileAddition.setFilePath("");
                fileAdditionMap.put(flagName[i], newFileAddition);
            }
        }

        QueryWrapper<ProjectInfo> queryWrapper3  = new QueryWrapper<>();
        queryWrapper3.eq("id", orderInfoProject.getProjectId());
        ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper3);
        if (projectInfo == null) {
            log.warn("getOrderById 项目信息为空！");
        }

        List<OrderPackageListInfoVO> orderPackageList = orderInfoPackageService.getOrderPackageList(id);
        if (orderPackageList == null) {
            log.warn("getOrderById 订单所含包件信息为空！");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("orderInfoProject", orderInfoProject);
        map.put("users", op);
        map.put("fileAdditionMap", fileAdditionMap);
        map.put("projectInfo", projectInfo);
        map.put("orderPackageList", orderPackageList);

        return Result.ok().data(map).code(1);
    }

    // 订单审核未通过
    @PostMapping("/updateOrderStatusError")
    public Result updateOrderStatusError(@RequestBody UpdateOrderStatusErrorVO updateOrderStatusErrorVO) throws IOException {
        QueryWrapper<OrderInfoProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", updateOrderStatusErrorVO.getId());
        OrderInfoProject orderInfoProject = orderInfoProjectService.getOne(queryWrapper);

        if (orderInfoProject.getStatus().equals(1)) {
            if (StringUtils.isAnyBlank(updateOrderStatusErrorVO.getAddition())) {
                orderInfoProject.setAdditionMessage("审核未通过，请前往查看。");
            }
            else {
                orderInfoProject.setAdditionMessage(updateOrderStatusErrorVO.getAddition());
            }
            orderInfoProject.setStatus(4);
            boolean flag = orderInfoProjectService.updateById(orderInfoProject);
            if (!flag) {
                return Result.error().code(1);
            }

            CreateJson createJson = new CreateJson();
            JSONObject json = createJson.getJson(0, orderInfoProject, projectInfoService);
            String strJson = json.toString();

            String url = "https://www.huaskj.com/bidding-test-api/user/wx/sendMessage";
//            String url = "http://192.168.2.115:8083/user/wx/sendMessage";

            URL apiUrl = new URL(url);

            HttpURLConnection conn =  (HttpURLConnection) apiUrl.openConnection();

            conn.setRequestMethod("POST");

            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(strJson.getBytes(String.valueOf(StandardCharsets.UTF_8)));
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            conn.disconnect();

            log.info(response.toString());

        }
        return Result.ok().code(1);
    }

    // 订单审核通过
    @PostMapping("/updateOrderStatus/{id}")
    public Result updateOrderStatus(@PathVariable Long id) throws IOException {
        QueryWrapper<OrderInfoProject> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        OrderInfoProject orderInfoProject = orderInfoProjectService.getOne(queryWrapper);
        if (orderInfoProject.getStatus().equals(-1)) {
            // 更新订单表status
            orderInfoProject.setStatus(1);
            boolean flag1 = orderInfoProjectService.updateById(orderInfoProject);
            // 更新订单包件表is_pay
            QueryWrapper<OrderInfoPackage> queryWrapper1 = new QueryWrapper<>();
            queryWrapper1.eq("order_id", orderInfoProject.getId());
            List<OrderInfoPackage> list = orderInfoPackageService.list(queryWrapper1);
            boolean flag2 = true;
            for (OrderInfoPackage orderInfoPackage : list) {
                orderInfoPackage.setIsPay(1);
                boolean f = orderInfoPackageService.updateById(orderInfoPackage);
                flag2 = f && flag2;
            }

            // 删除users备份表中信息并覆盖users
            QueryWrapper<UsersAudit> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("order_id", id);
            UsersAudit usersAudit = usersAuditService.getOne(queryWrapper2);
            QueryWrapper<Users> queryWrapper3 = new QueryWrapper<>();
            queryWrapper3.eq("open_id", usersAudit.getOpenId());
            Users users = usersService.getOne(queryWrapper3);
            users.setTelephone(usersAudit.getTelephone());
            users.setEmail(usersAudit.getEmail());
            users.setAddress(usersAudit.getAddress());
            users.setInvoiceTitle(usersAudit.getInvoiceTitle());
            users.setTaxNumber(usersAudit.getTaxNumber());
            users.setBank(usersAudit.getBank());
            users.setBankNumber(usersAudit.getBankNumber());
            users.setBankPhone(usersAudit.getBankPhone());
            users.setCompanyAddress(usersAudit.getCompanyAddress());
            users.setCompanyStatus(usersAudit.getCompanyStatus());
            users.setPhoneNum(usersAudit.getPhoneNum());
            users.setName(usersAudit.getName());
            users.setUnitname(usersAudit.getUnitname());
            boolean update1 = usersService.updateById(users);
//            boolean remove1 = usersAuditService.removeById(usersAudit);

            // 删除file_addition备份表中信息并更新
            boolean flag3 = true;
            QueryWrapper<FileAdditionAudit> queryWrapper4 = new QueryWrapper<>();
            queryWrapper4.eq("open_id", users.getOpenId());
            List<FileAdditionAudit> fileAdditionAuditList = fileAdditionAuditService.list(queryWrapper4);
            for (FileAdditionAudit fileAdditionAudit : fileAdditionAuditList) {
                QueryWrapper<FileAddition> queryWrapper5 = new QueryWrapper<>();
                queryWrapper5.eq("open_id", fileAdditionAudit.getOpenId());
                queryWrapper5.eq("name", fileAdditionAudit.getName());
                FileAddition fileAddition = fileAdditionService.getOne(queryWrapper5);
                fileAddition.setFilePath(fileAdditionAudit.getFilePath());
                boolean up2 = fileAdditionService.updateById(fileAddition);
//                boolean re2 = fileAdditionAuditService.removeById(fileAdditionAudit);
                flag3 = up2 && flag3;
            }

            if (!flag1 || !flag2 || !flag3 || !update1) {
                return Result.error().code(1);
            }

        }
        if (orderInfoProject.getStatus().equals(1)) {
            orderInfoProject.setStatus(2);
            if (orderInfoProject.getIsToken().equals(2)) {
                orderInfoProject.setIsToken(3);
            }
            orderInfoProject.setAdditionMessage("审核已通过，请前往下载包件。");
            boolean flag = orderInfoProjectService.updateById(orderInfoProject);
            if (!flag) {
                log.error("订单状态更新失败！");
                return Result.error().code(1);
            }
            CreateJson createJson = new CreateJson();
            JSONObject json = createJson.getJson(1, orderInfoProject, projectInfoService);
            String strJson = json.toString();

            String url = "https://www.huaskj.com/bidding-test-api/user/wx/sendMessage";

//            String url = "http://192.168.2.48:8083/user/wx/sendMessage";
            URL apiUrl = new URL(url);

            HttpURLConnection conn =  (HttpURLConnection) apiUrl.openConnection();

            conn.setRequestMethod("POST");

            conn.setDoOutput(true);

            conn.setRequestProperty("Content-Type", "application/json");

            OutputStream os = conn.getOutputStream();
            os.write(strJson.getBytes(String.valueOf(StandardCharsets.UTF_8)));
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            conn.disconnect();

            log.info(response.toString());
        }
        return Result.ok().code(1);
    }

    @GetMapping("/downloadOrderList/{id}")
    public void downloadOrderList(@PathVariable Long id, HttpServletResponse response) throws IOException {
        QueryWrapper<PackageInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("project_id", id);
        List<PackageInfo> packageInfoList = packageInfoService.list(queryWrapper);

        QueryWrapper<ProjectInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", id);
        ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper1);

        HSSFWorkbook wb = orderInfoPackageService.downloadExcel(packageInfoList);
        OutputStream outputStream = response.getOutputStream();

        String fileName = projectInfo.getProjectName() + "报名信息.xls";
        try {
            fileName = URLEncoder.encode(fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-disposition", "attachment;filename="+fileName+";"+"filename*=utf-8''"+fileName);

        wb.write(outputStream);
        outputStream.close();
    }
}
