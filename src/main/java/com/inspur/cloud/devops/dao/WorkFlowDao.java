/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.WorkFlow;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WorkFlowDao extends BaseObjectDao<WorkFlow> {
    /**
     * query list
     * @param params
     * @return
     */
    public List<WorkFlow> getList(Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("is_deleted", Boolean.FALSE);
        String hql = "SELECT T from WorkFlow T WHERE T.objectId = :objectId and T.name = :name and T.isDeleted=:is_deleted order by startAt desc";
        return this.find(hql, params);
    }
}
