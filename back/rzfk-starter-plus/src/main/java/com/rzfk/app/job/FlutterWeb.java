package com.rzfk.app.job;


import com.rzfk.app.domain.AppSourceInfo;
import com.rzfk.app.service.IAppSourceInfoService;
import com.rzfk.common.jsonp.Flutter;
import com.rzfk.common.utils.IDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

//@Component
public class FlutterWeb {

    @Autowired
    private IAppSourceInfoService appSourceInfoService;

    @Scheduled(cron = "0 */1 * * * ?")
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void getList() throws Exception {
        for (int i = 98; i < 99; i++) {
            List<Map<String, String>> res = Flutter.getFlutterDemos(i + "");
            for (int j = 0; j < res.size(); j++) {
                AppSourceInfo appSourceInfo = new AppSourceInfo();
                appSourceInfo.setId(IDUtils.getId());
                appSourceInfo.setCreateTime(new Date());
                appSourceInfo.setName(res.get(j).get("title"));
                appSourceInfo.setType(res.get(j).get("tag"));
                appSourceInfo.setImg(res.get(j).get("img"));
                appSourceInfo.setUrl(res.get(j).get("url1"));
                appSourceInfo.setUploadTime(res.get(j).get("time"));
                appSourceInfo.setSeq("vue");
                appSourceInfoService.save(appSourceInfo);
            }
        }

    }
}
