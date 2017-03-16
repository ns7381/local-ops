/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.Location.LocationLocal;
import org.springframework.stereotype.Repository;
import org.springside.modules.orm.hibernate.HibernateDao;

import java.io.Serializable;

@Repository
public class LocationLocalDao extends HibernateDao<LocationLocal, Serializable> {
}
