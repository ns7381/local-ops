/**
 * 
 */
package com.inspur.cloud.devops.service;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.inspur.cloud.devops.entity.Application;
import com.inspur.cloud.devops.entity.Resource.ResourcePackage;
import com.inspur.cloud.devops.utils.FileUtils;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.ServletOutputStream;

/**
 * 应用安装部署信息 service
 * @author nathan<br>
 * @version 1.0
 * 2016-11-27 上午9:57:20<br>
 */
@Service
@Transactional
public class InstallService {
	/*
	@Autowired
	private InstallDao installDao;
	@Autowired
	private ResourcePackageService versionService;
	@Autowired
	private ApplicationService appService;

    public ResourceConfiguration save(ResourceConfiguration install){
        installDao.save(install);
        return install;
    }
    *//**
     * 删除应用安装信息，用于重新部署的情况
     * @param id
     *//*
    public void delete(String id){
        installDao.delete(id);
    }
    *//**
     * 修改应用安装部署信息
     * @param install
     * @return
     *//*
    public ResourceConfiguration update(ResourceConfiguration install){
        installDao.save(install);
        return install;
    }
	*//**
	 * 获取
	 * @param id
	 * @return
	 *//*
	public ResourceConfiguration get(String id){
		return installDao.get(id);
	}


	*//**
	 * 获取应用的安装信息
	 * @param appId
	 * @return
	 *//*
	public List<ResourceConfiguration> getAppInstalls(String appId) {
		return installDao.getAppInstalls(appId);
	}


    public void generateInstallWar(String installId, String versionId, ServletOutputStream outputStream) {
        ResourceConfiguration install = this.get(installId);
        ResourcePackage version = versionService.get(versionId);
        Application application = appService.get(install.getAppId());
        String unPublicFiles = application.getUnPublicFiles();
        String installWarPath = install.getWarDir();
        String versionWarPath = version.getWarDir();
        String newInstallWarFilePath = FileUtils.getInstallWarFilePath(application.getCode(), version.getCode(), install.getCode());
        File warDir = new File(newInstallWarFilePath);
        if (!warDir.exists()) {
            warDir.mkdirs();
        }
        try {
            String installWarUnzipPath = FileUtils.unZipFile(installWarPath, null);
            String versionWarUnzipPath = FileUtils.unZipFile(versionWarPath, null);
            Map<String, File> installFileMap = FileUtils.findFilesMap(new File(installWarUnzipPath));
            Map<String, File> versionFileMap = FileUtils.findFilesMap(new File(versionWarUnzipPath));
            replaceUnPublicFiles(installFileMap, versionFileMap, unPublicFiles);
            String path = FileUtils.zipFile(versionWarUnzipPath, null, newInstallWarFilePath, true);
            install.setWarDir(path);
            install.setVersion(version.getVersion());
            install.setVersionId(version.getId());
            this.update(install);
            org.apache.commons.io.FileUtils.copyFile(new File(path), outputStream);
            FileUtils.deleteFile(new File(installWarUnzipPath));
            FileUtils.deleteFile(new File(versionWarUnzipPath));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    *//**
     * 替换不需要发布的文件
     * @param srcFileMap
     * @param destFileMap
     *//*
    private void replaceUnPublicFiles(Map<String, File> srcFileMap, Map<String, File> destFileMap, String unPublicFiles) {
        for(String path : unPublicFiles.split(":")){
            if (!path.contains(File.separator)) {
                if (File.separator.equals("/")) {
                    path = path.replace("\\", File.separator);
                } else {
                    path = path.replace("/", File.separator);
                }
            }
            File srcFile = srcFileMap.get(path);
            File destFile = destFileMap.get(path);
            if(srcFile == null || destFile == null)
                return;
            try {
                org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void generateInstallPatch(String installId, String versionId, ServletOutputStream outputStream) {
        ResourceConfiguration install = this.get(installId);
        ResourcePackage version = versionService.get(versionId);
        Application application = appService.get(install.getAppId());
        String oldWarFilePath = install.getWarDir();
        String newWarFilePath = version.getWarDir();
        String patchPath = FileUtils.getInstallPatchFilePath(application.getCode(), version.getCode(), install.getCode());
        try {
            String newWarPath = FileUtils.unZipFile(newWarFilePath, null);
            String oldWarPath = FileUtils.unZipFile(oldWarFilePath, null);
            File newWarFile = new File(newWarPath);
            File oldWarFile = new File(oldWarPath);
            //获取文件相对父路径的key，file map
            Map<String, File> newMap = FileUtils.findFilesMap(newWarFile);
            Map<String, File> oldMap = FileUtils.findFilesMap(oldWarFile);
            //比较文件，获取新增和修改的文件map
            Map<String, File> changedFiles = FileUtils.getChangedFiles(newMap, oldMap);

            //去掉不需要发布的文件
            removeUnPublicFiles(changedFiles, application.getUnPublicFiles());

            //复制变动文件
            for(String key : changedFiles.keySet()){
                File srcFile = changedFiles.get(key);
                File destFile = new File(patchPath+"/application"+key);
                org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
                if(srcFile.getName().toLowerCase().endsWith("upgrade.sql")){
                    destFile = new File(patchPath+"/"+srcFile.getName());
                    org.apache.commons.io.FileUtils.copyFile(srcFile, destFile);
                }
            }
            //生成删除文件列表 文件
            Map<String, File> delFiles = FileUtils.getUnContain(oldMap, newMap);
            for(String path : application.getUnPublicFiles().split(":")){
                File file = new File(path);
                delFiles.remove(file.getPath());
            }
            genPatchDelFile(patchPath, delFiles);

            //生成版本文件
            genVersionFile(version, patchPath+"/application/WEB-INF");

            //压缩更新包
            String patch = FileUtils.zipFile(patchPath, null, null, true);
            install.setPatch(patch);
            install.setVersion(version.getVersion());
            install.setVersionId(version.getId());
            this.update(install);
            org.apache.commons.io.FileUtils.copyFile(new File(patch), outputStream);
            FileUtils.deleteFile(newWarFile);
            FileUtils.deleteFile(oldWarFile);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ZipException e) {
            e.printStackTrace();
        }

    }

    *//**
     * 将不需要发布的文件从文件map移除
     * @param changedFiles
     * @param unPublicFiles
     *//*
    private void removeUnPublicFiles(Map<String, File> changedFiles, String unPublicFiles) {
        for(String path : unPublicFiles.split(":")){
            if (!path.contains(File.separator)) {
                if (File.separator.equals("/")) {
                    path = path.replace("\\", File.separator);
                } else {
                    path = path.replace("/", File.separator);
                }
            }
            File file = new File(path);
            changedFiles.remove(file.getPath());
        }
    }

    *//**
     * 生成删除文件列表
     * @param patchPath
     * @param delFiles
     * @throws IOException
     *//*
    private void genPatchDelFile(String patchPath, Map<String, File> delFiles)
            throws IOException {
        File deleteListFile = new File(patchPath+"/files-to-del.txt");
        if (!deleteListFile.exists()) {
            FileUtils.createFile(patchPath + "/files-to-del.txt");
        }
        Writer out = new OutputStreamWriter(new FileOutputStream(deleteListFile), "utf-8");
        for(String key : delFiles.keySet()){
            String path = key;

            if(path.startsWith("\\")){
                path = path.substring(1);
            }

            StringBuffer sb = new StringBuffer();
            while(path.indexOf("\\")>-1){
                sb.append(path.substring(0, path.indexOf("\\")));
                sb.append("/");
                path = path.substring(path.indexOf("\\")+1);
            }
            sb.append(path);
            out.write(sb.toString());
            out.write("\r\n");
        }
        out.close();
    }
    *//**
     * 生成新的version文件
     * @param version
     * @return
     *//*
    private File genVersionFile(ResourcePackage version, String filePath) {
        String path = "version";
        if(filePath != null){
            File pathFile = new File(filePath);
            if(!pathFile.exists()){
                pathFile.mkdirs();
            }
            path = filePath + File.separator + "version";
        }
        File versionFile = new File(path);
        FileWriter fw;
        try {
            fw = new FileWriter(versionFile);
            fw.write("version=" + version.getVersion());
            fw.write("\r\n");
            fw.write("app.id=" + version.getAppId());
            fw.write("\r\n");
            fw.write("date=" + new Date());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException("无法生成version文件:"+e.getMessage());
        }
        return versionFile;
    }*/
}
