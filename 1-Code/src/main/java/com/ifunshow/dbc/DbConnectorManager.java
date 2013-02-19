package com.ifunshow.dbc;

import java.util.HashMap;

/**
 * 
 * @author 于亚丰
 *
 */
public class DbConnectorManager {
	private static HashMap<String,DbConnector> connectorMap;
	
	private DbConnectorManager(){
		connectorMap = new HashMap<String, DbConnector>();
	}
	
	private static DbConnectorManager instance = null;
	public synchronized static DbConnectorManager getInstance() {
		if(null == instance){
		   instance = new DbConnectorManager();
		}
		return instance;
	}
	
	synchronized void putDbConnectorMap(DbConnector connector){
		if(null == connectorMap){
			connectorMap = new HashMap<String, DbConnector>();
		}
		if(!connectorMap.containsKey(connector.getConnectorID())){
			connectorMap.put(connector.getConnectorID(),connector);
		}
	}
	
	public DbConnector getDbConnector(String connectorID){
		return connectorMap.get(connectorID);
	}
	
	public DbConnector getDbConnector(String dbType, String dbVersion, String ip_host,
			Integer port, String db, String userName, String password){
		String connectorID = "db_connector_"+ip_host+"_"+port+"_"+db+"_"+userName;
		DbConnector connector;
		if(connectorMap.containsKey(connectorID)){
			connector = connectorMap.get(connectorID);
		}else{
			connector = new DbConnector(dbType, dbVersion, ip_host,
					port, db, userName, password);
			getInstance().putDbConnectorMap(connector);
		}
		return connector;
	}	
	
}
