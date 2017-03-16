/**
 * 
 */
package com.inspur.cloud.devops.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Type;

import com.inspur.cloud.entity.IdEntity;

@MappedSuperclass
public abstract class BaseObject extends IdEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = -192375636795802942L;

	/**
	 * 名称
	 */
	protected String name;
	
	/**
	 * 描述
	 */
	protected String description;
	
	  
	private Boolean isDeleted;
	private Date createdAt;
	private Date updatedAt;
	private Date deletedAt;
	/**
	 * @return 名称
	 */
	@Column(nullable = false, unique = true)
	public String getName() {
		return name;
	}

	/**
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return 描述
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description 描述
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the isDeleted
	 */
	@Type(type="com.inspur.cloudframework.hibernate.type.CharacterBooleanType")
	public Boolean getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the updatedAt
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @return the deletedAt
	 */
	public Date getDeletedAt() {
		return deletedAt;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @param deletedAt the deletedAt to set
	 */
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
