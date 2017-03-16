package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.DeploymentTopologyDao;
import com.inspur.cloud.devops.entity.deployment.DeploymentTopology;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;

@Service
@Transactional
public class DeploymentTopologyService {
	
	@Autowired
	private DeploymentTopologyDao dao;

	public DeploymentTopology get(String id) {
		return dao.get(id);
	}

	public DeploymentTopology create(DeploymentTopology shell){
		dao.save(shell);
		return shell;
	}

    public List<DeploymentTopology> getAll() {
        return dao.getAll();
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public DeploymentTopology update(DeploymentTopology shell){
        Assert.notNull(shell.getId());
        DeploymentTopology db = this.get(shell.getId());
        BeanUtils.copyNotNullProperties(shell, db);
        dao.save(db);
        return db;
	}
}
