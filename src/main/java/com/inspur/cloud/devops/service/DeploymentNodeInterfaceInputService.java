package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.DeploymentNodeInterfaceInputDao;
import com.inspur.cloud.devops.entity.deployment.DeploymentNodeInterfaceInput;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class DeploymentNodeInterfaceInputService {
	
	@Autowired
	private DeploymentNodeInterfaceInputDao dao;

	public DeploymentNodeInterfaceInput get(String id) {
		return dao.get(id);
	}

	public DeploymentNodeInterfaceInput create(DeploymentNodeInterfaceInput deploymentNodeInterfaceInput){
		dao.save(deploymentNodeInterfaceInput);
		return deploymentNodeInterfaceInput;
	}

    public List<DeploymentNodeInterfaceInput> getByInterfaceId(String deploymentNodeInterfaceId) {
        return dao.findBy("deploymentNodeInterfaceId", deploymentNodeInterfaceId);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public DeploymentNodeInterfaceInput update(DeploymentNodeInterfaceInput deploymentNodeInterfaceInput){
        Assert.notNull(deploymentNodeInterfaceInput.getId());
        DeploymentNodeInterfaceInput db = this.get(deploymentNodeInterfaceInput.getId());
        BeanUtils.copyNotNullProperties(deploymentNodeInterfaceInput, db);
        dao.save(db);
        return db;
	}

}
