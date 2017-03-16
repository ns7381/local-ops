package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.LicenseInfoDao;
import com.inspur.cloud.devops.entity.LicenseInfo;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.List;

@Service
@Transactional
public class LicenseInfoService {
	
	@Autowired
	private LicenseInfoDao dao;
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	public LicenseInfo get(String id){
		return dao.get(id);
	}

	/**
	 * 获取全部的数据列表
	 * @return
	 */
	public List<LicenseInfo> getAll() {
		return dao.getAll();
	}

    /**
     * 新增
     * @return
     */
    public LicenseInfo add(LicenseInfo licenseInfo) {
        dao.save(licenseInfo);
        return licenseInfo;
    }

    /**
     * 编辑
     * @return
     */
    public LicenseInfo edit(LicenseInfo licenseInfo) {
        Assert.notNull(licenseInfo.getId());
        LicenseInfo db = this.get(licenseInfo.getId());
        BeanUtils.copyNotNullProperties(licenseInfo, db);
        dao.save(db);
        return db;
    }

    public void delete(String id) {
        Assert.hasLength(id);
        dao.delete(id);
    }
}
