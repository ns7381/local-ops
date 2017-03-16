package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.DeploymentNodeAttributeDao;
import com.inspur.cloud.devops.entity.deployment.DeploymentNodeAttribute;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class DeploymentNodeAttributeService {
	
	@Autowired
	private DeploymentNodeAttributeDao dao;

	public DeploymentNodeAttribute get(String id) {
		return dao.get(id);
	}

	public DeploymentNodeAttribute create(DeploymentNodeAttribute deploymentNodeAttribute){
		dao.save(deploymentNodeAttribute);
		return deploymentNodeAttribute;
	}

    public List<DeploymentNodeAttribute> getByNodeId(String nodeId) {
        return dao.findBy("deploymentNodeId", nodeId);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public DeploymentNodeAttribute update(DeploymentNodeAttribute deploymentNodeAttribute){
        Assert.notNull(deploymentNodeAttribute.getId());
        DeploymentNodeAttribute db = this.get(deploymentNodeAttribute.getId());
        BeanUtils.copyNotNullProperties(deploymentNodeAttribute, db);
        dao.save(db);
        return db;
	}

}
