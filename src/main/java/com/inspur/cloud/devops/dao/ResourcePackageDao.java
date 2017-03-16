/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.Application;
import com.inspur.cloud.devops.entity.Resource.ResourcePackage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ResourcePackageDao extends BaseObjectDao<ResourcePackage> {
	
    public List<ResourcePackage> getList(Map<String, Object> params) {
        params.put("isDeleted", Boolean.FALSE);
        String hql = "SELECT T from ResourcePackage T where T.deploymentId=:deploymentId and T.isDeleted=:isDeleted ORDER BY T.updatedAt DESC";
        return this.find(hql, params);
    }
}
