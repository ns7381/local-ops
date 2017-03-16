package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.deployment.DeploymentNode;
import com.inspur.cloud.devops.service.DeploymentNodeService;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value="/topology/node")
public class DeploymentNodeController {

    @Autowired
	private DeploymentNodeService service;

	/**
	 * 新增应用程序 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<DeploymentNode> addApp(@RequestBody DeploymentNode deploymentNode){
		return ModelAndViewUtils.success(service.create(deploymentNode));
	}

    /**
     * 删除应用程序
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
	public ModelAndView<DeploymentNode> edit(@RequestBody DeploymentNode deploymentNode){
		return ModelAndViewUtils.success(service.update(deploymentNode));
	}

	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<DeploymentNode>> getByTopologyId(@RequestParam String topologyId){
		return ModelAndViewUtils.success(service.getByTopologyId(topologyId));
	}
}
