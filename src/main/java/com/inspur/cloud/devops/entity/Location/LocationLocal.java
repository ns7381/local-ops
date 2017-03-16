package com.inspur.cloud.devops.entity.Location;

import com.inspur.cloud.devops.entity.deployment.NodeType;
import com.inspur.cloud.entity.IdEntity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/13.
 */
@Entity
@Table(name="location_local")
public class LocationLocal extends IdEntity {
    private String locationId;
    private String ip;
    private NodeType type;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
