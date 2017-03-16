/**
 * 
 */
package com.inspur.cloud.devops.service;

import java.util.List;

import com.inspur.cloud.devops.dao.AppDao;
import com.inspur.cloud.devops.entity.Application;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.util.Assert;

@Service
@Transactional
public class ApplicationService {
	
	@Autowired
	private AppDao applicationDao;

	@Autowired
	private ResourcePackageService versionService;
	
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	public Application get(String id){
		return applicationDao.get(id);
	}

	/**
	 * 获取全部的app数据列表
	 * @return
	 */
	public List<Application> getAll() {
		return applicationDao.getAll();
	}

    /**
     * 新增应用
     * @return
     */
    public Application addApp(Application application) {
        applicationDao.save(application);
        return application;
    }

    /**
     * 新增应用
     * @return
     */
    public Application editApp(Application application) {
        Assert.notNull(application.getId());
        Application db = this.get(application.getId());
        BeanUtils.copyNotNullProperties(application, db);
        applicationDao.save(db);
        return db;
    }

    public void deleteApp(String id) {
        Assert.hasLength(id);
        applicationDao.delete(id);
    }
}
