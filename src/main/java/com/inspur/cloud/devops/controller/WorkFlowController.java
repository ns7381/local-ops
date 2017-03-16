package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.WorkFlow;
import com.inspur.cloud.devops.service.WorkFlowService;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping(value="/workflow")
public class WorkFlowController {

    @Autowired
	private WorkFlowService service;

	/**
	 * 新增应用程序 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<WorkFlow> create(@RequestBody WorkFlow workFlow){
		return ModelAndViewUtils.success(service.save(workFlow));
	}

    /**
     * 删除应用程序
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ModelAndView<Boolean> delete(@PathVariable String id){
        service.delete(id);
        return ModelAndViewUtils.success(Boolean.TRUE);
    }

	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView<WorkFlow> edit(@RequestBody WorkFlow workFlow){
		return ModelAndViewUtils.success(service.update(workFlow));
	}
	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<WorkFlow>> getByObjectId(@RequestParam String objectId){
		return ModelAndViewUtils.success(service.getByObjectId(objectId));
	}

}
