package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.Resource.Repository;
import com.inspur.cloud.devops.service.RepositoryService;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping(value="/resource/repository")
public class RepositoryController {

    @Autowired
	private RepositoryService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<Repository> add(@RequestBody Repository repository){
		return ModelAndViewUtils.success(service.add(repository));
	}

    /**
     * 删除
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView<Boolean> delete(@PathVariable String id){
        service.delete(id);
        return ModelAndViewUtils.success(Boolean.TRUE);
    }

    /**
     * 获取所有分支
     * @return
     */
    @RequestMapping(value = "/{id}/branches", method = RequestMethod.GET)
    public ModelAndView<List<String>> getBranches(@PathVariable String id) {
        return ModelAndViewUtils.success(service.getBranches(id));
    }

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView<Repository> edit(@RequestBody Repository repository){
		return ModelAndViewUtils.success(service.edit(repository));
	}

	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<Repository>> getAll(){
		return ModelAndViewUtils.success(service.getAll());
	}

}
