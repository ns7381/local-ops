package com.inspur.cloud.devops.controller;

import com.inspur.cloud.devops.entity.Location.LocationLocal;
import com.inspur.cloud.devops.service.LocationLocalService;
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
@RequestMapping(value="/location/local")
public class LocationLocalController {

    @Autowired
	private LocationLocalService service;

	/**
	 * 新增
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView<LocationLocal> add(@RequestBody LocationLocal location){
		return ModelAndViewUtils.success(service.create(location));
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
	public ModelAndView<LocationLocal> edit(@RequestBody LocationLocal location){
		return ModelAndViewUtils.success(service.update(location));
	}

	/**
	 * 获取所有数据
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET )
	public ModelAndView<List<LocationLocal>> getByLocationId(@RequestParam String locationId){
		return ModelAndViewUtils.success(service.getByLocationId(locationId));
	}

}
