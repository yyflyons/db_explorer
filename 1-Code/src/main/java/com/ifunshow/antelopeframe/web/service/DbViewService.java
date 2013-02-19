package com.ifunshow.antelopeframe.web.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import com.ifunshow.antelopeframe.web.base.BaseService;
import com.ifunshow.dbc.util.QueryRunnerHelper;

/**
 * 角色管理
 * @author yyflyons-于亚丰
 */
@Service
public class DbViewService extends BaseService{
	
	public List<Map<String, Object>> getSchemas(){
		List list = null;
		try {
			QueryRunnerHelper runner = new QueryRunnerHelper("mysql","5","localhost",3306,"","root","root");
			list = runner.queryAllSchemaList();
		} catch (SQLException e) {
			
		}
		return list;
	}

	public List getSchemaTables(String schemaName) {
		List list = null;
		try {
			QueryRunnerHelper runner = new QueryRunnerHelper("mysql","5","localhost",3306,"","root","root");
			list = runner.queryAllTablesList(schemaName);
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public List getSchemaTableColumns(String schemaName,String tableName) {
		List list = null;
		try {
			QueryRunnerHelper runner = new QueryRunnerHelper("mysql","5","localhost",3306,"","root","root");
			list = runner.queryAllColumnsList(schemaName,tableName);
		} catch (SQLException e) {
			
		}
		return list;
	}
	
	public List querySchemaTableData(String schemaName,String tableName) {
		List list = null;
		try {
			QueryRunnerHelper runner = new QueryRunnerHelper("mysql","5","localhost",3306,"","root","root");
			list = runner.querySchemaTableData(schemaName,tableName,0,1000);
		} catch (SQLException e) {
			
		}
		return list;
	}

}
