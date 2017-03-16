package com.inspur.cloud.devops.entity.deployment;

import com.inspur.cloud.devops.entity.BaseObject;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Administrator on 2017/1/13.
 */
@Entity
@Table(name="deployment_node")
public class DeploymentNode extends BaseObject {
    private String deploymentTopologyId;

    public String getDeploymentTopologyId() {
        return deploymentTopologyId;
    }

    public void setDeploymentTopologyId(String deploymentTopologyId) {
        this.deploymentTopologyId = deploymentTopologyId;
    }
}
