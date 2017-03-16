package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.DeploymentNodeDao;
import com.inspur.cloud.devops.entity.deployment.DeploymentNode;
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
public class DeploymentNodeService {
	
	@Autowired
	private DeploymentNodeDao dao;

	public DeploymentNode get(String id) {
		return dao.get(id);
	}

	public DeploymentNode create(DeploymentNode shell){
		dao.save(shell);
		return shell;
	}

    public List<DeploymentNode> getByTopologyId(String topologyId) {
        return dao.findBy("deploymentTopologyId", topologyId);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public DeploymentNode update(DeploymentNode shell){
        Assert.notNull(shell.getId());
        DeploymentNode db = this.get(shell.getId());
        BeanUtils.copyNotNullProperties(shell, db);
        dao.save(db);
        return db;
	}

}
