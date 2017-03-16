/**
 * 
 */
package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.LocationDao;
import com.inspur.cloud.devops.entity.Location.Location;
import com.inspur.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Transactional
public class LocationService {
	
	@Autowired
	private LocationDao locationDao;
	
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	public Location get(String id){
		return locationDao.get(id);
	}

	/**
	 * 获取全部的数据列表
	 * @return
	 */
	public List<Location> getAll() {
		return locationDao.getAll();
	}

    /**
     * 新增
     * @return
     */
    public Location add(Location location) {
        locationDao.save(location);
        return location;
    }

    /**
     * 新增应用
     * @return
     */
    public Location edit(Location location) {
        Assert.notNull(location.getId());
        Location db = this.get(location.getId());
        BeanUtils.copyNotNullProperties(location, db);
        locationDao.save(db);
        return db;
    }

    public void delete(String id) {
        Assert.hasLength(id);
        locationDao.delete(id);
    }
}
