/**
 * 
 */
package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.Application;
import com.inspur.cloud.devops.entity.LicenseInfo;
import com.inspur.cloud.entity.IdEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import org.springside.modules.orm.hibernate.HibernateDao;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class LicenseInfoDao extends HibernateDao<LicenseInfo, Serializable> {

}
