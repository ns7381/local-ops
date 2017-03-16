package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.LicenseInfo;
import com.inspur.cloud.devops.service.LicenseInfoService;
import com.inspur.cloudframework.license.License;
import com.inspur.cloudframework.license.LicenseDeploy;
import com.inspur.cloudframework.license.manager.LicenseManager;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/1/13.
 */
@Controller
@RequestMapping(value = "/license")
public class LicenseInfoController {
    @Autowired
    private LicenseInfoService service;
    /**
     * 新增
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView<LicenseInfo> add(@RequestBody LicenseInfo licenseInfo){
        return ModelAndViewUtils.success(service.add(licenseInfo));
    }

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView<Boolean> delete(@PathVariable String id){
        service.delete(id);
        return ModelAndViewUtils.success(Boolean.TRUE);
    }

    /**
     * 编辑
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView<LicenseInfo> edit(@RequestBody LicenseInfo licenseInfo){
        return ModelAndViewUtils.success(service.edit(licenseInfo));
    }

    /**
     * 获取所有数据
     * @return
     */
    @RequestMapping(method = RequestMethod.GET )
    public ModelAndView<List<LicenseInfo>> getAll(){
        return ModelAndViewUtils.success(service.getAll());
    }
    /**
     * 获取安装信息的license
     * @param response
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/file")
    @ResponseBody
    public void getUserInstallLicense(HttpServletResponse response){
        try {
            List<LicenseInfo> licenseInfos = service.getAll();
            if(licenseInfos.isEmpty()){
                throw new RuntimeException("无法找到用户的应用信息，请联系管理员确认");
            }
            License license = new License();
            Map<String, LicenseDeploy> deploys = new HashMap<String, LicenseDeploy>();
            LicenseDeploy licenseDeploy = new LicenseDeploy();
            for (LicenseInfo licenseInfo : licenseInfos) {
                licenseDeploy.setEndDate(licenseInfo.getEndDate());
                licenseDeploy.setServiceLimit(licenseInfo.getServiceLimit());
                licenseDeploy.setServerLimit(licenseInfo.getServerLimit());
                licenseDeploy.setType(licenseInfo.getType());
                licenseDeploy.setOrgName(licenseInfo.getOrgName());
                deploys.put(licenseInfo.getOrgCode(), licenseDeploy);
            }
            license.setDeploys(deploys);
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("licenseInfo/octet-stream;charset=UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode("ver.dat", "UTF-8"));

            LicenseManager.generateLicense(license, response.getOutputStream());

        } catch (IOException e) {
            throw new RuntimeException("获取license出现错误！"+e.getMessage());
        }
    }
}
