package database;

import java.util.Iterator;
import java.util.LinkedHashMap;

import util.StringUtils;

public class SqlUtil {

	public String generateSelectSql(SqlEntity entity) {
		String str = iscanGenerate(entity,1);
		if(!str.startsWith("0")){
			return str;
		}
		StringBuilder sql = new StringBuilder();
		StringBuilder sets = new StringBuilder();
		StringBuilder wheres = new StringBuilder();
		
		Iterator<String> it = entity.column.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			sets.append(key).append(",");
		}
		StringUtils.deleteLastChat(sets);
		
		it = entity.whereColumn.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			wheres.append(" and ").append(key);
			if(entity.whereColumn.get(key).trim().startsWith("(")){
				wheres.append(" in ");
			}else{
				wheres.append("=");
			}
			wheres.append(entity.whereColumn.get(key)).append("");
		}
		
		sql.append("SELECT ").append(sets);
		sql.append(" FROM ").append(entity.getTable());
		sql.append(" WHERE 1=1 ").append(wheres);
		return sql.toString();
	}
	
	public String generateInsertSql(SqlEntity entity) {
		String str = iscanGenerate(entity,1);
		if(!str.startsWith("0")){
			return str;
		}
		StringBuilder sql = new StringBuilder();
		StringBuilder keys = new StringBuilder();
		StringBuilder values = new StringBuilder();
//		link map = new HashedMap(entity.column);
//		MapIterator it = map.mapIterator();
		Iterator<String> it = entity.column.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			keys.append(key).append(",");
			values.append(entity.column.get(key)).append(",");
		}
		StringUtils.deleteLastChat(keys);
		StringUtils.deleteLastChat(values);
		sql.append("INSERT INTO ").append(entity.getTable());
		sql.append(" (").append(keys).append(") values (").append(values).append(")");
		return sql.toString();
	}
	
	public  String generateUpdateSql(SqlEntity entity){
		String str = iscanGenerate(entity,1);
		if(!str.startsWith("0")){
			return str;
		}
		StringBuilder sql = new StringBuilder();
		StringBuilder sets = new StringBuilder();
		StringBuilder wheres = new StringBuilder();
		
		Iterator<String> it = entity.column.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			sets.append(key).append("=").append(entity.column.get(key)).append(",");
		}
		StringUtils.deleteLastChat(sets);
		
		it = entity.whereColumn.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			wheres.append(" and ").append(key).append("=").append(entity.whereColumn.get(key)).append("");
		}
		
		sql.append("UPDATE ").append(entity.getTable());
		sql.append(" SET ").append(sets);
		sql.append(" WHERE 1=1 ").append(wheres);
		return sql.toString();
	}
	
	public String generateDeleteSql(SqlEntity entity){
		String str = iscanGenerate(entity,1);
		if(!str.startsWith("0")){
			return str;
		}
		StringBuilder sql = new StringBuilder();
		StringBuilder wheres = new StringBuilder();

		Iterator<String> it = entity.whereColumn.keySet().iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			wheres.append(" and ").append(key).append("=").append(entity.whereColumn.get(key)).append("");
		}
		
		sql.append("DELETE FROM ").append(entity.getTable());
		sql.append(" WHERE 1=1 ").append(wheres);
		return sql.toString();
	}
	
	
	/**
	 * 检查条件是否可以生成sql
	 * @param entity
	 * @param type 1,检查column 2,检查where
	 * @return
	 */
	private String iscanGenerate(SqlEntity entity,int type) {
		String rv = "0";
		if(StringUtils.isNull(entity.table, true))
			return "set table first";
		switch(type){
		case 1:
			if(entity.column.size()==0)
				rv = "please add column entry";
			break;
		case 2:
			if(entity.whereColumn.size()==0)
				return "please add whereColumn entry";
			break;
		}
		return rv;
	}
	public static void main(String[] args) {
		String d = "sdf;";
		System.out.print(d.split(";").length);
		SqlUtil su = new SqlUtil();
		SqlEntity e = su.new SqlEntity();
		e.setTable("ss");
		e.addColumn("s1", "4");
		e.addColumn("s2", "empty()");
		e.addColumn("s3", "3");
		e.addColumn("s7", "1");
		e.addColumn("24", "6");
		e.addColumn("253", "88?");
		e.addWhereColumn("s4", "g");
		e.addWhereColumn("s5", "ss");
		System.out.println(su.generateDeleteSql(e));
		System.out.println(su.generateUpdateSql(e));
		System.out.println(su.generateInsertSql(e));
		SqlEntity e1 = su.new SqlEntity();
		e1.setTable("clickstat");
		e1.addColumn("support", "4");
		e1.addColumn("oppo", "3");
		e1.addColumn("gradecount", "1");
		e1.addColumn("grade", "6");
		e1.addWhereColumn("clickid", "33");
		System.out.println( su.generateSelectSql(e1));
	
	}

	public class SqlEntity{
		private String table;
		private LinkedHashMap<String, String> column;
		private LinkedHashMap<String, String> whereColumn;
		public SqlEntity(){
			column = new LinkedHashMap<String, String>();
			whereColumn = new LinkedHashMap<String, String>();			
		}
		public String getTable() {
			return table;
		}
		public void setTable(String table) {
			this.table = table;
		}
		public void addColumn(String columnName,String columnValue){
			addValue(column, columnName, columnValue);
		}
		/**
		 * @param columnName
		 * @param columnValue
		 * @param defaultmodify 是否允许自动加上'
		 */
		public void addColumn(String columnName,String columnValue,boolean defaultmodify){
			if(defaultmodify){
				addValue(column, columnName, columnValue);
			}else{
				addValue(column, columnName, columnValue, defaultmodify);
			}
		}
		public void removeColumn(String columnName,String columnValue){
			column.remove(columnName);
		}
		public void addWhereColumn(String columnName,String columnValue){
			addValue(whereColumn, columnName, columnValue);
		}
		public void addWhereColumn(String columnName,String columnValue,boolean defaultmodify){
			addValue(whereColumn, columnName, columnValue,defaultmodify);
		}
		public void removeWhereColumn(String columnName,String columnValue){
			whereColumn.remove(columnName);
		}
		
		/**
		 * 根据如这样的nodeid,articleid,displayorder,attr,pubtime,publishstate,title,masterid自动加上column,,value皆为?
		 * @param cols 以逗号,分隔
		 */
		public void addColumns(String cols){
			String[] col = cols.split(",");
			for(int i=0;i<col.length;i++){
				addColumn(col[i], "?");
			}
		}
		private void addValue(LinkedHashMap<String, String> map,String columnName,String columnValue){
			if(StringUtils.isNull(columnName,true)){
				return;
			}
			if(StringUtils.isNull(columnValue,true) || columnValue.equals("?")){
				map.put(columnName, "?");
			}else if(columnValue.endsWith("()") || columnValue.equalsIgnoreCase("sysdate")){
				map.put(columnName, columnValue);
			}else{
				map.put(columnName, "'"+columnValue+"'");
			}
		}
		private void addValue(LinkedHashMap<String, String> map,String columnName,String columnValue,boolean defaultmodify){
			if(defaultmodify){
				addValue(map, columnName, columnValue);
			}else{
				if(StringUtils.isNull(columnName,true)){
					return;
				}
				map.put(columnName, columnValue);
			}
		}
	}
}