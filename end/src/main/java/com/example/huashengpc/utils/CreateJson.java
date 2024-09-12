package com.example.huashengpc.utils;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.huashengpc.models.domain.OrderInfoProject;
import com.example.huashengpc.models.domain.ProjectInfo;
import com.example.huashengpc.service.ProjectInfoService;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateJson {
    public JSONObject getJson(int flag, OrderInfoProject orderInfoProject, ProjectInfoService projectInfoService) {
        QueryWrapper<ProjectInfo> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("id", orderInfoProject.getProjectId());
        ProjectInfo projectInfo = projectInfoService.getOne(queryWrapper1);

        JSONObject json = new JSONObject();
        json.put("touser", orderInfoProject.getOpenId());
        json.put("template_id", "qlHUD4HsPe8tkfeZ1uu1hb34H6ODmSPi3pxlQBnilFQ");
        String pageUrl = "pages/tenderPage/tendDatil?item=" + orderInfoProject.getId().toString();
        json.put("page", pageUrl);
        json.put("miniprogram_state", "trial");
        json.put("lang", "zh_CN");

        SimpleDateFormat formateer = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        String formattedDate = formateer.format(date);

        String str1, str2;

        if (flag==1) {
            str1 = "已通过";
            str2 = "审核已通过，请前往下载包件。";
        }
        else {
            str1 = "未通过";
            str2 = orderInfoProject.getAdditionMessage();
        }

        if(str2.length()>20){
            str2=str2.substring(0, 20);
        }

        String[] dataName = {"thing19", "time9", "time17", "thing14", "thing5"};
        String[] dataValue = {projectInfo.getProjectName(),
                formateer.format(orderInfoProject.getOrderTime()),
                formattedDate,
                str1,
                str2};
        JSONObject Data = new JSONObject();
        for (int i = 0; i < dataName.length; i++) {
            JSONObject Value = new JSONObject();
            Value.put("value", dataValue[i]);
            Data.put(dataName[i], Value);
        }

        json.put("data", Data);
        return json;
    }
}
