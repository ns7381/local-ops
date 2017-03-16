package com.inspur.cloud.devops.entity.Location;

import com.inspur.cloud.devops.entity.BaseObject;

/**
 * Created by Administrator on 2017/1/13.
 */
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="location")
public class Location extends BaseObject {
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
