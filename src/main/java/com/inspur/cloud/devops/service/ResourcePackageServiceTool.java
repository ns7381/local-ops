package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.ResourcePackageDao;
import com.inspur.cloud.devops.entity.Application;
import com.inspur.cloud.devops.entity.Resource.Repository;
import com.inspur.cloud.devops.entity.Resource.ResourcePackage;
import com.inspur.cloud.devops.utils.FileUtils;
import com.inspur.cloud.devops.utils.LocalExecuteCommand;
import com.inspur.common.utils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.eclipse.jgit.api.CreateBranchCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ResourcePackageServiceTool {
	@Autowired
	private ResourcePackageDao dao;
	public ResourcePackage save(ResourcePackage db){
        dao.save(db);
        return db;
	}
}
