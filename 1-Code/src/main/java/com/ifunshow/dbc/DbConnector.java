package com.ifunshow.dbc;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.commons.dbutils.DbUtils;
import com.ifunshow.dbc.classloader.JdbcDriverLoader;
import com.ifunshow.dbc.util.DBHelper;

/**
 * 
 * @author 于亚丰
 *
 */
public class DbConnector {
	private URLClassLoader urlClassLoader;
	private String dbType;
	private String dbVersion;
	private String ip_host;
	private Integer port;
	private String db;
	private String userName;
	private String password;
	
	private DbConnector(){}
	
	/**
	 * 构造函数
	 * @param dbType 数据库类型
	 * @param dbVersion  数据库版本
	 * @param ip 所在IP
	 * @param port 所用端口
	 * @param db  库实例名（oracle中）
	 * @param userName 连接用户名
	 * @param password 连接密码
	 */
	public DbConnector(String dbType, String dbVersion, String ip_host,
			Integer port, String db, String userName, String password) {
		this.dbType = dbType;
		this.dbVersion = dbVersion;
		this.ip_host = ip_host;
		this.port = port;
		this.db = db;
		this.userName = userName;
		this.password = password;
		init();
	}

	public void init(){
		try {
			urlClassLoader = DBHelper.decideDriverLoader(dbType, dbVersion);
			initScanDbConnection(urlClassLoader,DBHelper.decideDriverString(dbType), DBHelper.decideDbUrlString(dbType,ip_host,port,db), userName, password);
		} catch (MalformedURLException e) {
			//e.printStackTrace();
		} catch (SecurityException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		} catch (SQLException e) {
			//e.printStackTrace();
		} catch (NoSuchMethodException e) {
			//e.printStackTrace();
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		} catch (InstantiationException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}
		
		//List<Map<String, Object>> a = getScanedQueryRunner().query(scanedConnection, "SELECT trim(T.USERNAME) SCHEMANAME FROM ALL_USERS T", new MapListHandler());
	}
	

	private Connection scanedConnection;//被扫描数据库连接
	public Connection getScanedConnection() {
		return scanedConnection;
	}
	
	/**
	 * 初始化被扫描库的数据库连接
	 * @param driver
	 * @param url
	 * @param user
	 * @param pwd
	 * @throws SQLException
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws IllegalArgumentException 
	 * @throws SecurityException 
	 */
	public void initScanDbConnection(URLClassLoader urlClassLoader,String driver,String url,String user,String pwd) throws SQLException, SecurityException, IllegalArgumentException, NoSuchMethodException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException{
		DbUtils.loadDriver(urlClassLoader,driver);
		JdbcDriverLoader.loadDriver(urlClassLoader, driver);
		Properties conProps = new Properties(); 
		conProps.put("user", user); 
		conProps.put("password", pwd); 
		conProps.put("defaultRowPrefetch", "20"); 
		//conProps.put("internal_logon", "sysdba"); 
		scanedConnection = DriverManager.getConnection(url, conProps);
	}
	
	public String getConnectorID(){
		return "db_connector_"+ip_host+"_"+port+"_"+db+"_"+userName;
	}

}
