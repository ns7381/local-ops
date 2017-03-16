package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.RepositoryDao;
import com.inspur.cloud.devops.entity.Resource.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RepositoryServiceTool {
	
	@Autowired
	private RepositoryDao dao;

    /**
     * 保存
     * @return
     */
    public Repository save(Repository entity) {
        dao.save(entity);
        return entity;
    }
}
