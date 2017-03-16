/**
 * 
 */
package com.inspur.cloud.devops.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springside.modules.orm.hibernate.HibernateDao;

import com.inspur.cloud.devops.entity.BaseObject;

/**
 * 
 * @author cww<br>
 * @version 1.0
 * 2015-6-3 10:52:49<br>
 */
public class BaseObjectDao<T extends BaseObject> extends HibernateDao<T, String> {

    /**
     * 按属性查找对象列表, 匹配方式为相等.
     *
     * @param propertyName
     * @param value
     */
    @Override
    public List<T> findBy(String propertyName, Object value) {
        List<T> tmpList = super.findBy(propertyName, value);
        List<T> rst = new ArrayList<T>();
        for (T t : tmpList) {
            if (!t.getIsDeleted()) {
                rst.add(t);
            }
        }
        return rst;
    }

    /**
	 * The t parameter must be a Resource object.
	 * <p></p>
	 */
	public void save(T t) {
		this.save(t, StringUtils.isEmpty(t.getId()), null);
	}
	
	public void save(T t, String type){
		this.save(t, StringUtils.isEmpty(t.getId()), type);
	}

	public void save(T t, boolean isCreate){
		this.save(t, isCreate, null);
	}
	
	/**
	 * The t parameter must be a Resource object.
	 * <p></p>
	 */
	public void save(T t, boolean isCreate, String type) {
		//create a resource
		
		Date time = new Date();
		if(t.getUpdatedAt() == null) {
			t.setUpdatedAt(time);
		}
		if(isCreate) {
			if(t.getCreatedAt() == null) {
				t.setCreatedAt(time);
			}
			t.setIsDeleted(Boolean.FALSE);
		}
		super.save(t);
	}
	
//	private void setProperty()
	
	public void delete(String id) {
		//check the resource has referenced by any other resource
		T t = this.get(id);
		Date time = new Date();
		t.setUpdatedAt(time);
		t.setDeletedAt(time);
		t.setIsDeleted(Boolean.TRUE);
		this.save(t);
	}


	/* (non-Javadoc)
	 * @see org.springside.modules.orm.hibernate.SimpleHibernateDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(BaseObject entity) {
		this.delete(entity.getId());
	}
	
	

}
