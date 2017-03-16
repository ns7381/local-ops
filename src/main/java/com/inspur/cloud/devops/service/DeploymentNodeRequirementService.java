package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.DeploymentNodeRequirementDao;
import com.inspur.cloud.devops.entity.deployment.DeploymentNodeRequirement;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class DeploymentNodeRequirementService {
	
	@Autowired
	private DeploymentNodeRequirementDao dao;

	public DeploymentNodeRequirement get(String id) {
		return dao.get(id);
	}

	public DeploymentNodeRequirement create(DeploymentNodeRequirement deploymentNodeRequirement){
		dao.save(deploymentNodeRequirement);
		return deploymentNodeRequirement;
	}

    public List<DeploymentNodeRequirement> getByNodeId(String nodeId) {
        return dao.findBy("deploymentNodeId", nodeId);
    }

    public DeploymentNodeRequirement getByNodeIdAndName(String nodeId, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("deploymentNodeId", nodeId);
        params.put("name", name);
        String hql = "SELECT T from DeploymentNodeRequirement T where T.deploymentNodeId=:deploymentNodeId and T.name=:name";
        return dao.findUnique(hql, params);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public DeploymentNodeRequirement update(DeploymentNodeRequirement deploymentNodeRequirement){
        Assert.notNull(deploymentNodeRequirement.getId());
        DeploymentNodeRequirement db = this.get(deploymentNodeRequirement.getId());
        BeanUtils.copyNotNullProperties(deploymentNodeRequirement, db);
        dao.save(db);
        return db;
	}

}
