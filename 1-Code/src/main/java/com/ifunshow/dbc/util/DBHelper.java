package com.ifunshow.dbc.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ifunshow.dbc.classloader.JarUtil;


public class DBHelper {	
	private static HashMap<String, String> sqlMap;
	static{
		sqlMap = new HashMap<String, String>();
		SAXReader saxReader = new SAXReader();
		Document document;
		try {
			document = saxReader.read(new File("H:\\GitRepo\\db_explorer\\1-Code\\src\\main\\resources\\com\\ifunshow\\dbc\\DbSqlMapper.xml"));
			Element root = document.getRootElement();
			for (Iterator iter = root.elementIterator(); iter.hasNext(); ) {
				Element element = (Element) iter.next();
				sqlMap.put(element.attribute("id").getValue(), element.getText());
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		 
	}
	
	//根据数据库类型和版本，目前这部分是写在代码中的，有可能的话要配置实现
	public static URLClassLoader decideDriverLoader(String dbType,String dbVersion) throws MalformedURLException{
		URL[] urls = null;
		JarUtil ju = new JarUtil(JarUtil.class);
		String jarPath = ju.getJarPath()+File.separator+"jdbcjar"+File.separator;
		System.out.println("jarPath==>"+jarPath);
		if("oracle".equalsIgnoreCase(StringUtils.trim(dbType))){
			urls = new URL[1];
			if("8".equalsIgnoreCase(dbVersion)){
				urls[0] = new File(jarPath+"oracle_classes12.jar").toURL();
			}else{
				urls[0] = new File(jarPath+"oracle_ojdbc6.jar").toURL();
			}
		}else if("teradata".equalsIgnoreCase(StringUtils.trim(dbType))){
			urls = new URL[2];
			urls[0] = new File(jarPath+"teradata_tdgssconfig.jar").toURL();
			urls[1] =  new File(jarPath+"teradata_terajdbc4.jar").toURL();
		}else if("db2".equalsIgnoreCase(StringUtils.trim(dbType))){
			urls = new URL[2];
			if("8".equalsIgnoreCase(dbVersion)){
				urls[0] = new File(jarPath+"db2_db2jcc-v8.jar").toURL();
				urls[1] = new File(jarPath+"db2_db2jcc_license_cu-v8.jar").toURL();
			}else{
				urls[0] = new File(jarPath+"db2_db2jcc-v9.7.jar").toURL();
				urls[1] = new File(jarPath+"db2_db2jcc_license_cu-v9.7.jar").toURL();
			}
		}else if("mysql".equalsIgnoreCase(StringUtils.trim(dbType))){
			urls = new URL[1];
			urls[0] = new File(jarPath+"mysql_connector_5.1.15_bin.jar").toURL();
		}
		return  new URLClassLoader(urls,ClassLoader.getSystemClassLoader());
	}
	
	//根据数据库类型，返回相应数据库驱动串
	public static String decideDriverString(String dbType){
		String driver = null;
		if("oracle".equalsIgnoreCase(StringUtils.trim(dbType))){
			driver = "oracle.jdbc.OracleDriver";
		}else if("teradata".equalsIgnoreCase(StringUtils.trim(dbType))){
			driver = "com.ncr.teradata.TeraDriver";
		}else if("db2".equalsIgnoreCase(StringUtils.trim(dbType))){
			driver = "com.ibm.db2.jcc.DB2Driver";
		}else if("mysql".equalsIgnoreCase(StringUtils.trim(dbType))){
			driver = "com.mysql.jdbc.Driver";
		}
		
		return driver;
	}
	
	//根据数据库类型，返回相应数据库连接串
	public static String decideDbUrlString(String dbType,String ip_host,Integer port,String dbName){
		String url = null;
		String address = StringUtils.isNotBlank(ip_host)?ip_host:"127.0.0.1";
		if("oracle".equalsIgnoreCase(StringUtils.trim(dbType))){
			url = "jdbc:oracle:thin:@"+address+":"+port+"/"+dbName;//实际在oracle库中会遇到需要用tns串来拼接这个url，具体原因未知
		}else if("teradata".equalsIgnoreCase(StringUtils.trim(dbType))){
			url = "jdbc:teradata://"+address+"/CLIENT_CHARSET=EUC_CN,TMODE=TERA,CHARSET=ASCII";
		}else if("db2".equalsIgnoreCase(StringUtils.trim(dbType))){
			url = "jdbc:db2://"+address+":"+port+"/"+dbName;
		}else if("mysql".equalsIgnoreCase(StringUtils.trim(dbType))){
			url = "jdbc:mysql://"+address+":"+port+"/"+dbName;
		}
		
		return url;
	}
	
	
	
	public static String getQuerySQL(String dbType,String SqlKey){
		String sqlStr = null;
		if(sqlMap.containsKey(dbType.toUpperCase()+"."+SqlKey.toUpperCase())){
			sqlStr = sqlMap.get(dbType.toUpperCase()+"."+SqlKey.toUpperCase());
		}
		return sqlStr;
	}
	
}
