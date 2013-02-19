package com.ifunshow.dbc.classloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 读取jar文件
 * 
 * @author 于亚丰
 * 
 */
public class ReadJarFile {
	List<String> jarList = new ArrayList<String>();
	List<String> filesURL = new ArrayList<String>();

	/**
	 * 读取指定文件夹的文件
	 * 
	 * @param jarFileName
	 *            路径
	 * @param strings
	 *            后缀
	 */
	public ReadJarFile(String jarFileName, String[] strings) {
		// TODO Auto-generated constructor stub
		File f = new File(jarFileName);
		File[] fl = f.listFiles();
		for (File file : fl) {
			for (String str : strings) {
				if (file.getName().endsWith(str)) {
					jarList.add(file.getName());
					filesURL.add(file.toURI().toString());
				}
			}

		}
	}

	/**
	 * 获取文件名
	 * 
	 * @return
	 */
	public List<String> getFiles() {
		// TODO Auto-generated method stub
		return filesURL;
	}

	/**
	 * 获取文件路径
	 * 
	 * @return
	 */
	public List<String> getFilesURL() {
		// TODO Auto-generated method stub
		return filesURL;
	}

}
