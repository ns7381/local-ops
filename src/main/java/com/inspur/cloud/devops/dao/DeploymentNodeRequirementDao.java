package com.inspur.cloud.devops.dao;

import com.inspur.cloud.devops.entity.deployment.DeploymentNodeRequirement;
import org.springside.modules.orm.hibernate.HibernateDao;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/1/13.
 */
@org.springframework.stereotype.Repository
public class DeploymentNodeRequirementDao extends HibernateDao<DeploymentNodeRequirement, Serializable> {
}
