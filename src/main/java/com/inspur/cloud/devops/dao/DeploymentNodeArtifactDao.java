/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeArtifact;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DeploymentNodeArtifactDao extends BaseObjectDao<DeploymentNodeArtifact> {
    /**
     * query list
     * @param params
     * @return
     */
    public List<DeploymentNodeArtifact> getList(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("is_deleted", Boolean.FALSE);
        String hql = "SELECT T from DeploymentNodeArtifact T WHERE T.name = :name and T.isDeleted=:is_deleted";
        return this.find(hql, params);
    }
}
