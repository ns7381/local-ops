package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.Resource.ResourcePackage;
import com.inspur.cloud.devops.entity.Resource.ResourcePackageStatus;
import com.inspur.cloud.devops.service.ResourcePackageService;
import com.inspur.cloud.devops.service.ResourcePackageServiceTool;
import com.inspur.cloud.devops.utils.FileUtils;
import com.inspur.cloud.devops.utils.ThreadWithEntity;
import com.inspur.cloud.devops.ws.WebSocketConstants;
import com.inspur.cloud.devops.ws.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springside.modules.utils.spring.SpringContextHolder;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping(value = "/resource/package")
public class ResourcePackageController {

    @Autowired
    private ResourcePackageService service;
    @Autowired
    private WebSocketHandler webSocketHandler;
    /**
     * 新增版本信息
     *
     * @param version
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView<ResourcePackage> create(@RequestBody ResourcePackage version) {
        return ModelAndViewUtils.success(service.create(version));
    }

    /**
     * 删除
     *
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView<Boolean> delete(@PathVariable String id) {
        service.delete(id);
        return ModelAndViewUtils.success(Boolean.TRUE);
    }

    /**
     * 修改版本信息
     *
     * @param version
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ModelAndView<ResourcePackage> update(@RequestBody ResourcePackage version) {
        return ModelAndViewUtils.success(service.update(version));
    }

    /**
     * 获取版本信息
     *
     * @param id
     * @return ResourcePackage
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView<ResourcePackage> get(@PathVariable String id) {
        return ModelAndViewUtils.success(service.get(id));
    }


    /**
     * 获取应用的所有版本信息
     *
     * @param params
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView<List<ResourcePackage>> getList(@RequestParam Map<String, Object> params) {
        return ModelAndViewUtils.success(service.getList(params));
    }

    @RequestMapping(value = "/{id}/download", method = RequestMethod.GET)
    @ResponseBody
    public void download(HttpServletResponse response,
                         @PathVariable("id") String id) {
        ResourcePackage resourcePackage = service.get(id);
        try {
            // 清空response
            response.reset();
            // 设置response的Header
            response.setContentType("licenseInfo/octet-stream;charset=UTF-8");
            File file = new File(resourcePackage.getWarPath());
            response.setHeader("Content-Disposition", "attachment;filename="
                    + java.net.URLEncoder.encode(file.getName(), "UTF-8"));
            org.apache.commons.io.FileUtils.copyFile(file, response.getOutputStream());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/patch", method = RequestMethod.POST)
    public void patch(
            @RequestParam("deploymentId") String deploymentId,
            @RequestParam("prePackageId") String prePackageId,
            @RequestParam("nextPackageId") String nextPackageId) {

        final ResourcePackage prePackage = service.get(prePackageId);
        final ResourcePackage nextPackage = service.get(nextPackageId);
        final String uploadPath = FileUtils.getPatchFilePath(nextPackage.getName()+"-patch");
        ResourcePackage patchPackage = new ResourcePackage();
        patchPackage.setDeploymentId(deploymentId);
        patchPackage.setName(prePackage.getVersion() + "To" + nextPackage.getVersion());
        patchPackage.setVersion(prePackage.getVersion() + "To" + nextPackage.getVersion());
        patchPackage.setDescription(prePackage.getVersion() + "版本至" + nextPackage.getVersion() + "版本的patch");
        patchPackage.setStatus(ResourcePackageStatus.COMPARE);
        service.create(patchPackage);
        new ThreadWithEntity<ResourcePackage>(patchPackage) {
            @Override
            public void run(ResourcePackage entity) {
                ResourcePackageServiceTool service = SpringContextHolder.getBean(ResourcePackageServiceTool.class);
                String patchPath = FileUtils.compareWar(prePackage.getWarPath(), nextPackage.getWarPath(), uploadPath, nextPackage.getVersion());
                entity.setWarPath(patchPath);
                entity.setStatus(ResourcePackageStatus.FINISH);
                service.save(entity);
                webSocketHandler.sendMsg(WebSocketConstants.PACKAGE_STATUS, entity);
            }
        }.start();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadWar(@RequestParam("file") final MultipartFile file,
                                           @RequestParam("name") String name,
                                           @RequestParam("version") String version,
                                           @RequestParam("deploymentId") String deploymentId,
                                           @RequestParam("description") String description) {
        final ResourcePackage resourcePackage = new ResourcePackage();
        resourcePackage.setName(name);
        resourcePackage.setDescription(description);
        resourcePackage.setVersion(version);
        resourcePackage.setDeploymentId(deploymentId);
        resourcePackage.setStatus(ResourcePackageStatus.SAVING);
        if (file != null && !file.getOriginalFilename().trim().equals("")) {
            service.create(resourcePackage);
            String uploadPath = FileUtils.getPackageFilePath(deploymentId, UUID.randomUUID().toString());
            String fileName = file.getOriginalFilename();
            final String filePath = uploadPath+File.separator + fileName;
            new ThreadWithEntity<ResourcePackage>(resourcePackage) {
                @Override
                public void run(ResourcePackage entity) {
                    File destination = new File(filePath);
                    try {
                        org.apache.commons.io.FileUtils.copyInputStreamToFile(file.getInputStream(), destination);
                    } catch (IOException e) {
                        throw new RuntimeException("保存war包失败！", e);
                    }
                    entity.setWarPath(destination.getAbsolutePath());
                    entity.setStatus(ResourcePackageStatus.FINISH);
                    service.create(entity);
                    webSocketHandler.sendMsg(WebSocketConstants.PACKAGE_STATUS, entity);
                }
            }.start();
        } else {
            throw new RuntimeException("war包为空！");
        }
    }

    @RequestMapping(value = "/git", method = RequestMethod.PUT)
    public void packageWar(@RequestBody ResourcePackage resourcePackage) {
        service.packageWar(resourcePackage);
    }
}
