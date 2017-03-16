package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeInterface;
import com.inspur.cloud.devops.service.DeploymentNodeInterfaceService;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/topology/node/interface")
public class DeploymentNodeInterfaceController {

    @Autowired
	private DeploymentNodeInterfaceService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<DeploymentNodeInterface> add(@RequestParam("file") MultipartFile file,
                                                     @RequestParam String name,
                                                     @RequestParam String deploymentNodeId,
                                                     @RequestParam String description){
        DeploymentNodeInterface nodeInterface = new DeploymentNodeInterface();
        nodeInterface.setName(name);
        nodeInterface.setDeploymentNodeId(deploymentNodeId);
        nodeInterface.setDescription(description);
        try {
            File destination = new File(com.inspur.cloud.devops.utils.FileUtils.getInterfaceFilePath() + File.separator + file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), destination);
            nodeInterface.setImplementation(destination.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ModelAndViewUtils.success(service.create(nodeInterface));
	}

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/upload")
	public ModelAndView<DeploymentNodeInterface> upload(@RequestParam("file") MultipartFile file,
                                                     @RequestParam String id){
        DeploymentNodeInterface nodeInterface = service.get(id);
        try {
            File destination = new File(com.inspur.cloud.devops.utils.FileUtils.getInterfaceFilePath() + File.separator + file.getOriginalFilename());
            FileUtils.copyInputStreamToFile(file.getInputStream(), destination);
            nodeInterface.setImplementation(destination.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ModelAndViewUtils.success(service.update(nodeInterface));
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
	public ModelAndView<DeploymentNodeInterface> edit(@RequestBody DeploymentNodeInterface deploymentNodeInterface){
		return ModelAndViewUtils.success(service.update(deploymentNodeInterface));
	}

	/**
	 * 获取数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView<List<DeploymentNodeInterface>> getList(@RequestParam Map<String, Object> params){
		return ModelAndViewUtils.success(service.getList(params));
	}
}
