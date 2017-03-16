/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.Application;
import org.springframework.stereotype.Repository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AppDao extends BaseObjectDao<Application> {
	
    public List<Application> getAll() {
        Map<String, Object> params = new HashMap<>();
        params.put("isDeleted", Boolean.FALSE);
        String hql = "SELECT T from Application T where T.isDeleted=:isDeleted ORDER BY T.updatedAt DESC";
        return this.find(hql, params);
    }
}
