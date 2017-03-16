package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.Location.Location;
import com.inspur.cloud.devops.service.LocationService;
import com.inspur.cloudframework.spring.web.bind.annotation.RequestBody;
import com.inspur.cloudframework.spring.web.servlet.ModelAndView;
import com.inspur.cloudframework.utils.web.ModelAndViewUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value="/location")
public class LocationController {

    @Autowired
	private LocationService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<Location> add(@RequestBody Location location){
		return ModelAndViewUtils.success(service.add(location));
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
	 * 编辑
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	public ModelAndView<Location> edit(@RequestBody Location location){
		return ModelAndViewUtils.success(service.edit(location));
	}

	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<Location>> getAll(){
		return ModelAndViewUtils.success(service.getAll());
	}

}
