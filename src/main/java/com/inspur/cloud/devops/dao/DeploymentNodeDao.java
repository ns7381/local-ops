/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.deployment.DeploymentNode;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeploymentNodeDao extends BaseObjectDao<DeploymentNode> {
    /**
     * query list
     * @param params
     * @return
     */
    public List<DeploymentNode> getList(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("is_deleted", Boolean.FALSE);
        String hql = "SELECT T from DeploymentNode T WHERE T.name = :name and T.isDeleted=:is_deleted";
        return this.find(hql, params);
    }
}
