package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.DeploymentNodePropertyDao;
import com.inspur.cloud.devops.entity.deployment.DeploymentNodeProperty;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class DeploymentNodePropertyService {
	
	@Autowired
	private DeploymentNodePropertyDao dao;

	public DeploymentNodeProperty get(String id) {
		return dao.get(id);
	}

	public DeploymentNodeProperty create(DeploymentNodeProperty deploymentNodeProperty){
		dao.save(deploymentNodeProperty);
		return deploymentNodeProperty;
	}

    public List<DeploymentNodeProperty> getByNodeId(String nodeId) {
        return dao.findBy("deploymentNodeId", nodeId);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public DeploymentNodeProperty update(DeploymentNodeProperty deploymentNodeProperty){
        Assert.notNull(deploymentNodeProperty.getId());
        DeploymentNodeProperty db = this.get(deploymentNodeProperty.getId());
        BeanUtils.copyNotNullProperties(deploymentNodeProperty, db);
        dao.save(db);
        return db;
	}

}
