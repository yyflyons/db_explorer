package com.ifunshow.dbc.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

import com.ifunshow.dbc.DbConnectorManager;


public class QueryRunnerHelper {

	private QueryRunner scanedQueryRunner = new QueryRunner();
	private Connection conn;
	private String dbType;
	
	public QueryRunnerHelper(String dbType, String dbVersion, String ip_host,
			Integer port, String db, String userName, String password){
		this.dbType = dbType;
		this.conn = DbConnectorManager.getInstance().getDbConnector(dbType, dbVersion, ip_host, port, db, userName, password).getScanedConnection();
	}
	
	
	/**
	 * 获取被扫描库的查询类
	 * @return
	 */
	private QueryRunner getScanedQueryRunner() {
		return (scanedQueryRunner!=null)?scanedQueryRunner:new QueryRunner();
	}
	
	public List<Map<String, Object>> queryAllSchemaList() throws SQLException {
		return getScanedQueryRunner().query(conn,DBHelper.getQuerySQL(dbType,"QUERY_ALL_SCHEMAS_SQL"),new MapListHandler());
	}


	public List<Map<String, Object>> queryAllTablesList(String schemaName) throws SQLException {
		return getScanedQueryRunner().query(conn,DBHelper.getQuerySQL(dbType,"QUERY_TABLES_BY_SCHEMA_SQL"),new MapListHandler(),schemaName);
	}


	public List<Map<String, Object>> queryAllColumnsList(String schemaName,String tableName) throws SQLException{
		return getScanedQueryRunner().query(conn,DBHelper.getQuerySQL(dbType,"QUERY_TABLE_COLUMNS_BY_SCHEMA_TABLE_SQL"),new MapListHandler(),schemaName,tableName);
	}
	
	public List<Map<String, Object>> querySchemaTableData(String schemaName,String tableName,long startIndex,int size) throws SQLException{
		String Sql = new String(DBHelper.getQuerySQL(dbType,"QUERY_TABLE_DATA_SQL")).replaceFirst("\\{SCHEMA\\}", schemaName).replaceFirst("\\{TABLE\\}", tableName);
		return getScanedQueryRunner().query(conn,Sql,new MapListHandler(),startIndex,size);
	}
	
}
