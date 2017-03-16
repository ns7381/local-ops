package com.inspur.cloud.devops.service;

import com.inspur.cloud.devops.dao.RepositoryDao;
import com.inspur.cloud.devops.entity.Resource.Repository;
import com.inspur.cloud.devops.entity.Resource.RepositoryStatus;
import com.inspur.cloud.devops.utils.FileUtils;
import com.inspur.cloud.devops.utils.ThreadWithEntity;
import com.inspur.cloud.devops.ws.WebSocketHandler;
import com.inspur.cloud.devops.ws.WebSocketConstants;
import com.inspur.common.utils.BeanUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springside.modules.utils.spring.SpringContextHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class RepositoryService {
	
	@Autowired
	private RepositoryDao dao;
    @Autowired
    private WebSocketHandler webSocketHandler;
	/**
	 * 获取数据
	 * @param id
	 * @return
	 */
	public Repository get(String id){
		return dao.get(id);
	}

	/**
	 * 获取全部的数据列表
	 * @return
	 */
	public List<Repository> getAll() {
		return dao.getAll();
	}

    /**
     * 新增
     * @return
     */
    public Repository add(Repository entity) {
        final String repositoryPath = FileUtils.getRepositoryPath(UUID.randomUUID().toString());
        entity.setLocalDir(repositoryPath);
        entity.setStatus(RepositoryStatus.CLONING);
        dao.save(entity);
        new ThreadWithEntity<Repository>(entity) {
            @Override
            public void run(Repository entity) {
                RepositoryServiceTool service = SpringContextHolder.getBean(RepositoryServiceTool.class);
                try {
                    Git result = Git.cloneRepository()
                            .setURI(entity.getRepositoryUrl())
                            .setDirectory(new File(repositoryPath))
                            .setCloneSubmodules(true)
                            .setBranch("master")
                            .setCredentialsProvider(new UsernamePasswordCredentialsProvider(entity.getUsername(), entity.getPassword()))
                            .call();
                    entity.setStatus(RepositoryStatus.CLONED);
                    service.save(entity);
                    webSocketHandler.sendMsg(WebSocketConstants.REPOSITORY_STATUS, entity);
                } catch (Exception e) {
                    entity.setStatus(RepositoryStatus.FAIL);
                    service.save(entity);
                    webSocketHandler.sendMsg(WebSocketConstants.REPOSITORY_STATUS, entity);
                    e.printStackTrace();
                }
            }
        }.start();
        return entity;
    }

    public List<Repository> getByAppId(String appId) {
        return dao.findBy("appId", appId);
    }

    /**
     * 保存
     * @return
     */
    private Repository save(Repository entity) {
        dao.save(entity);
        return entity;
    }
    /**
     * 编辑
     * @return
     */
    public Repository edit(Repository entity) {
        Assert.notNull(entity.getId());
        Repository db = this.get(entity.getId());
        BeanUtils.copyNotNullProperties(entity, db);
        dao.save(db);
        return db;
    }

    public void delete(String id) {
        Assert.hasLength(id);
        try {
            org.apache.commons.io.FileUtils.deleteDirectory(new File(this.get(id).getLocalDir()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        dao.delete(id);
    }

    public List<String> getBranches(String id) {
        Repository repository = this.get(id);
        List<String> results = new ArrayList<>();
        try {
            Collection<Ref> refs = Git.lsRemoteRepository()
                    .setHeads(true)
                    .setTags(true)
                    .setRemote(repository.getRepositoryUrl())
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(repository.getUsername(), repository.getPassword()))
                    .call();
            for (Ref ref : refs) {
                results.add(ref.getName());
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return results;
    }
}
