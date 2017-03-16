package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeArtifact;
import com.inspur.cloud.devops.service.DeploymentNodeArtifactService;
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
@RequestMapping(value="/topology/node/artifact")
public class DeploymentNodeArtifactController {

    @Autowired
	private DeploymentNodeArtifactService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<DeploymentNodeArtifact> add(@RequestBody DeploymentNodeArtifact deploymentNodeArtifact){
		return ModelAndViewUtils.success(service.create(deploymentNodeArtifact));
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
	public ModelAndView<DeploymentNodeArtifact> edit(@RequestBody DeploymentNodeArtifact deploymentNodeArtifact){
		return ModelAndViewUtils.success(service.update(deploymentNodeArtifact));
	}

	/**
	 * 获取数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<DeploymentNodeArtifact>> getByNodeId(@RequestParam String nodeId){
		return ModelAndViewUtils.success(service.getByNodeId(nodeId));
	}
}
