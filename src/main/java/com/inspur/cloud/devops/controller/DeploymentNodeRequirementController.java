package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeRequirement;
import com.inspur.cloud.devops.service.DeploymentNodeRequirementService;
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
@RequestMapping(value="/topology/node/requirement")
public class DeploymentNodeRequirementController {

    @Autowired
	private DeploymentNodeRequirementService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<DeploymentNodeRequirement> add(@RequestBody DeploymentNodeRequirement deploymentNodeRequirement){
		return ModelAndViewUtils.success(service.create(deploymentNodeRequirement));
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
	public ModelAndView<DeploymentNodeRequirement> edit(@RequestBody DeploymentNodeRequirement deploymentNodeRequirement){
		return ModelAndViewUtils.success(service.update(deploymentNodeRequirement));
	}

	/**
	 * 获取数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<DeploymentNodeRequirement>> getByNodeId(@RequestParam String nodeId){
		return ModelAndViewUtils.success(service.getByNodeId(nodeId));
	}
}
