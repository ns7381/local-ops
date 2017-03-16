package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.deployment.Deployment;
import com.inspur.cloud.devops.service.DeploymentService;
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
import java.util.Map;

@Controller
@RequestMapping(value="/deployment")
public class DeploymentController {

    @Autowired
	private DeploymentService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<Deployment> create(@RequestBody Deployment deployment){
		return ModelAndViewUtils.success(service.create(deployment));
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
     * 部署
     * @return
     */
    @RequestMapping(value = "/{id}/interface/{interfaceId}", method = RequestMethod.PUT)
    public ModelAndView<Boolean> doInterface(@PathVariable String id, @PathVariable String interfaceId, @RequestParam Map<String, Object> params){
        return ModelAndViewUtils.success(service.doInterface(id, interfaceId, params));
    }

	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<Deployment>> getAll(){
		return ModelAndViewUtils.success(service.getAll());
	}

}
