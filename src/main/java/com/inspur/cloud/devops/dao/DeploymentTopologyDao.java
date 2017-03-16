/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.deployment.DeploymentTopology;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeploymentTopologyDao extends BaseObjectDao<DeploymentTopology> {
	
    public List<DeploymentTopology> getAll() {
        Map<String, Object> params = new HashMap<>();
        params.put("isDeleted", Boolean.FALSE);
        String hql = "SELECT T from DeploymentTopology T where T.isDeleted=:isDeleted ORDER BY T.updatedAt DESC";
        return this.find(hql, params);
    }
}
