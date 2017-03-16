package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.WorkFlowDao;
import com.inspur.cloud.devops.entity.WorkFlow;
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
public class WorkFlowService {
	
	@Autowired
	private WorkFlowDao dao;

	public WorkFlow get(String id) {
		return dao.get(id);
	}

	public WorkFlow save(WorkFlow workFlow){
		dao.save(workFlow);
		return workFlow;
	}

    public List<WorkFlow> getByObjectId(String objectId) {
        Map<String, Object> params = new HashMap<>();
        params.put("objectId", objectId);
        return dao.getList(params);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public WorkFlow update(WorkFlow workFlow){
        Assert.notNull(workFlow.getId());
        WorkFlow db = this.get(workFlow.getId());
        BeanUtils.copyNotNullProperties(workFlow, db);
        dao.save(db);
        return db;
	}

}
