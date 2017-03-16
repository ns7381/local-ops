package com.inspur.cloud.devops.controller;

import java.util.List;
import com.inspur.cloud.devops.entity.Application;
import com.inspur.cloud.devops.service.ApplicationService;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;

@Controller
@RequestMapping(value="/application")
public class ApplicationController {

    @Autowired
	private ApplicationService service;

	/**
	 * 新增应用程序 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<Application> addApp(@RequestBody Application application){
		return ModelAndViewUtils.success(service.addApp(application));
	}

    /**
     * 删除应用程序
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView<Boolean> delete(@PathVariable String id){
        service.deleteApp(id);
        return ModelAndViewUtils.success(Boolean.TRUE);
    }

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView<Application> edit(@RequestBody Application application){
		return ModelAndViewUtils.success(service.editApp(application));
	}

	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<Application>> getAll(){
		return ModelAndViewUtils.success(service.getAll());
	}

}
