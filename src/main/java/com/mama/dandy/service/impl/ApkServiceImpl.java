package com.mama.dandy.service.impl;

import java.io.File;

import org.springframework.stereotype.Service;

import com.mama.dandy.bo.UpdateApkBo;
import com.mama.dandy.common.SystemConstant;
import com.mama.dandy.service.ApkService;

@Service
public class ApkServiceImpl implements ApkService {
	
	@Override
	public String updateApk(String appName) {
		File file;
		if(appName.equals(SystemConstant.APP_NAME_APPLE)){
			file = new File(SystemConstant.APK_FILE_PATH+SystemConstant.APP_NAME_APPLE);
		}else{
			file = new File(SystemConstant.APK_FILE_PATH+SystemConstant.APP_NAME_ANDROID);
		}
		File[] files = file.listFiles();
		String version=null;
		String downLoadFileName = null;
		long lastModifyTime=0l;
		if(files.length>0){
			lastModifyTime=files[0].lastModified();
		}
		for(int i =0;i<files.length;i++){
			File apk =  files[i];
			String fileName = apk.getAbsolutePath();
			if(apk.isFile()){
				if(fileName.lastIndexOf(".")!=-1 && fileName.indexOf("apk")!=-1){
					if(i+1<files.length && lastModifyTime<files[i+1].lastModified())
						lastModifyTime  = files[i+1].lastModified();
				}
			}
		}
		
		for(File apk : files){
			String fileName = apk.getAbsolutePath();
			if(apk.isFile()){
				if(fileName.lastIndexOf(".")!=-1 && fileName.indexOf("apk")!=-1){
					if(apk.lastModified()==lastModifyTime){
						version = fileName.substring(fileName.lastIndexOf(SystemConstant.APK_PREFIX)+SystemConstant.APK_PREFIX.length(),fileName.lastIndexOf("."));
						downLoadFileName = apk.getAbsolutePath();
					}
				}
			}
		}
		
		return downLoadFileName;
	}

	
	public static void main(String[] args) {
		ApkServiceImpl impl = new ApkServiceImpl();
		UpdateApkBo bo = new UpdateApkBo();
		bo.setVersion("897897");
	}

}
