package com.inspur.cloud.devops.entity;

import com.inspur.cloud.entity.IdEntity;
import com.inspur.cloudframework.license.LicenseType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/13.
 */
@Entity
@Table(name="LICENSE_INFO")
public class LicenseInfo extends IdEntity {
    private LicenseType type;
    private Integer serverLimit;
    private Integer serviceLimit;
    private String orgCode;
    private Date endDate;
    private String orgName;
    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    @Enumerated(EnumType.STRING)
    public LicenseType getType() {
        return type;
    }

    public void setType(LicenseType type) {
        this.type = type;
    }

    public Integer getServerLimit() {
        return serverLimit;
    }

    public void setServerLimit(Integer serverLimit) {
        this.serverLimit = serverLimit;
    }

    public Integer getServiceLimit() {
        return serviceLimit;
    }

    public void setServiceLimit(Integer serviceLimit) {
        this.serviceLimit = serviceLimit;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
