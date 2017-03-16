/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeInterface;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeploymentNodeInterfaceDao extends BaseObjectDao<DeploymentNodeInterface> {
    /**
     * query list
     * @param params
     * @return
     */
    public List<DeploymentNodeInterface> getList(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("is_deleted", Boolean.FALSE);
        String hql = "SELECT T from DeploymentNodeInterface T WHERE T.name = :name and T.deploymentNodeId = :deploymentNodeId and T.isDeleted=:is_deleted";
        return this.find(hql, params);
    }
}
