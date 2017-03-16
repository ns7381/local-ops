/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.deployment.Deployment;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeploymentDao extends BaseObjectDao<Deployment> {
	
    public List<Deployment> getAll() {
        Map<String, Object> params = new HashMap<>();
        params.put("isDeleted", Boolean.FALSE);
        String hql = "SELECT T from Deployment T where T.isDeleted=:isDeleted ORDER BY T.updatedAt DESC";
        return this.find(hql, params);
    }
}
