package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.LocationLocalDao;
import com.inspur.cloud.devops.entity.Location.LocationLocal;
import com.inspur.cloud.devops.entity.deployment.NodeType;
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
public class LocationLocalService {
	
	@Autowired
	private LocationLocalDao dao;

	public LocationLocal get(String id) {
		return dao.get(id);
	}

	public LocationLocal create(LocationLocal locationLocal){
		dao.save(locationLocal);
		return locationLocal;
	}

    public List<LocationLocal> getByLocationId(String locationId) {
        return dao.findBy("locationId", locationId);
    }

    public List<LocationLocal> getByLocationIdAndType(String locationId, NodeType nodeType) {
        Map<String, Object> params = new HashMap<>();
        params.put("locationId", locationId);
        params.put("type", nodeType);
        String hql = "SELECT T from LocationLocal T where T.locationId=:locationId and T.type=:type";
        return dao.find(hql, params);
    }

    public void delete(String id) {
        dao.delete(id);
    }

	public LocationLocal update(LocationLocal locationLocal){
        Assert.notNull(locationLocal.getId());
        LocationLocal db = this.get(locationLocal.getId());
        BeanUtils.copyNotNullProperties(locationLocal, db);
        dao.save(db);
        return db;
	}

}
