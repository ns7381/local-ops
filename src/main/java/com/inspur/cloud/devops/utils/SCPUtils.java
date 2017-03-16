package com.inspur.cloud.devops.utils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.inspur.cloud.devops.entity.Location.LocationLocal;
import com.inspur.cloud.devops.entity.deployment.DeploymentNodeInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */
public class SCPUtils {
    public static void uploadFilesToServer(LocationLocal host, String localFile, String remoteFileName, String remoteTargetDirectory, String mode) throws IOException {
        Connection con = new Connection(host.getIp());
        con.connect();
        if (con.authenticateWithPassword(host.getUsername(), host.getPassword())) {
            System.out.println(host.getIp() +" scp auth success. Then transfer file " + remoteFileName);
            SCPClient scpClient = con.createSCPClient();
            scpClient.put(localFile, remoteFileName, remoteTargetDirectory, mode);
        }
        con.close();
    }

    public static void uploadFilesToServer(LocationLocal host, String localFile, String remoteTargetDirectory, String mode) throws IOException {
        Connection con = new Connection(host.getIp());
        con.connect();
        if (con.authenticateWithPassword(host.getUsername(), host.getPassword())) {
            System.out.println(host.getIp() + " scp auth success. Then transfer shell script.");
            SCPClient scpClient = con.createSCPClient();
            scpClient.put(localFile, remoteTargetDirectory, mode);
        }
        con.close();
    }
}
