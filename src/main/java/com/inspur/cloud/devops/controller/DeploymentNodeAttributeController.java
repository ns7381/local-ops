package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeAttribute;
import com.inspur.cloud.devops.service.DeploymentNodeAttributeService;
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
@RequestMapping(value="/topology/node/attribute")
public class DeploymentNodeAttributeController {

    @Autowired
	private DeploymentNodeAttributeService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<DeploymentNodeAttribute> add(@RequestBody DeploymentNodeAttribute deploymentNodeAttribute){
		return ModelAndViewUtils.success(service.create(deploymentNodeAttribute));
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
	public ModelAndView<DeploymentNodeAttribute> edit(@RequestBody DeploymentNodeAttribute deploymentNodeAttribute){
		return ModelAndViewUtils.success(service.update(deploymentNodeAttribute));
	}

	/**
	 * 获取数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<DeploymentNodeAttribute>> getByNodeId(@RequestParam String nodeId){
		return ModelAndViewUtils.success(service.getByNodeId(nodeId));
	}
}
