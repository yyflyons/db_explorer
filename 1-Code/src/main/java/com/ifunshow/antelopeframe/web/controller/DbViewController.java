package com.ifunshow.antelopeframe.web.controller;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ifunshow.antelopeframe.web.base.BaseController;
import com.ifunshow.antelopeframe.web.service.DbViewService;

/**
 * 角色管理
 * @author yyflyons-于亚丰
 */
@Controller
@RequestMapping("/dbview")
public class DbViewController  extends BaseController{

	@Autowired
	private DbViewService dbViewService;
	
	@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST})
	public String index(){
		return "dbview/dbinfo";
	}
	
	
	@ResponseBody
	@RequestMapping("/view")
	public String dbview(@RequestParam(value="schema",required=false) String schema,@RequestParam(value="table",required=false) String table,@RequestParam(value="type",required=false) String type){
		List list = null;
		String schemaName = StringUtils.trimToNull(schema);
		String tableName = StringUtils.trimToNull(table);
		type = StringUtils.trimToNull(type);
		if("schema".equalsIgnoreCase(type)){
			list = dbViewService.getSchemaTables(schemaName);
			if(null == list || list.isEmpty()){
				return "[]";
			}else{
				String str = "";
				String sc = super.getContextPath();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map object = (Map) iterator.next();
					str += ",{name:'"+object.get("TABLE_NAME")+"',table:'"+object.get("TABLE_NAME")+"',schema:'"+schemaName+"',isParent:true,type:'table',icon:'"+sc+"/resources/zTree/css/zTreeStyle/img/dbc/table.png'}";
				}
				return "["+str.replaceFirst(",", "")+"]";
			}
		}else if("table".equalsIgnoreCase(type)){
			list = dbViewService.getSchemaTableColumns(schemaName,tableName);
			if(null == list || list.isEmpty()){
				return "[]";
			}else{
				String str = "";
				String sc = super.getContextPath();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map object = (Map) iterator.next();
					str += ",{name:'"+object.get("COLUMN_NAME")+","+object.get("COLUMN_TYPE")+"',column:'"+object.get("COLUMN_NAME")+"',table:'"+tableName+"',schema:'"+schemaName+"',isParent:false,type:'column',icon:'"+sc+"/resources/zTree/css/zTreeStyle/img/dbc/column.png'}";
				}
				return "["+str.replaceFirst(",", "")+"]";
			}
		}else{
			list = dbViewService.getSchemas();
			if(null == list || list.isEmpty()){
				return "[]";
			}else{
				String str = "";
				String sc = super.getContextPath();
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					Map object = (Map) iterator.next();
					str += ",{name:'"+object.get("SCHEMA_NAME")+"',schema:'"+object.get("SCHEMA_NAME")+"',isParent:true,type:'schema',icon:'"+sc+"/resources/zTree/css/zTreeStyle/img/dbc/database.png'}";
				}
				return "["+str.replaceFirst(",", "")+"]";
			}
		}
	}
	
	@RequestMapping("/data")
	public String tableData(Model model,@RequestParam(value="schema",required=true) String schema,@RequestParam(value="table",required=true) String table){
		String schemaName = StringUtils.trimToNull(schema);
		String tableName = StringUtils.trimToNull(table);
		model.addAttribute("list", dbViewService.querySchemaTableData(schemaName,tableName));
		return "dbview/tabledata";
	}
	
}
