package operation;

import java.sql.Connection;


import database.ConnectionManager;
import database.DBUtil;
import database.SqlUtil;

import entity.Request;

public class DBSaver implements ISaver {

	private static String countNumSql = "select clickid from clickstat where clickid=";
	private static SqlUtil sqlutil = new SqlUtil(); 
	public void save(Request request) {
		Connection conn = null;
		try{
			conn = ConnectionManager.getInstance().getConnection();
			Object params[] = null;
			String sql = null;
			String id = request.getArtid().split(";")[0];
//			for(int i=0;i<ids[i].length();i++){
				if(DBUtil.getIntFromDatabase(countNumSql+id, conn)>0){
					sql = generateUpdateClickSql(request,id);
//					params = new Object[]{
//							request.getGrade(),
//							request.getSupport(),
//							request.getOppo(),
//							id	
//					};
				}else{
					sql = generateInsertClickSql(request,id);
//					params = new Object[]{
//							id,
//							request.getPageurl(),
//							request.getSupport(),
//							request.getOppo(),
//							request.getGrade()
//					};				
				}
				DBUtil.executeSql(conn, sql, params);
//			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn);
		}
	}

	public static String generateInsertClickSql(Request request,String id){
		SqlUtil.SqlEntity entity = sqlutil.new SqlEntity();
		entity.setTable("clickstat");
		entity.addColumn("clickid", id);
		entity.addColumn("pageurl", "n");
		entity.addColumn("support", request.getSupport()>0?"1":"0");
		entity.addColumn("oppo", request.getOppo()>0?"1":"0");
		entity.addColumn("grade", request.getGrade()>0?"1":"0");
		entity.addColumn("gradeCount", request.getGrade()>0?"1":"0");
		return sqlutil.generateInsertSql(entity);
	}
	public static String generateUpdateClickSql(Request request,String id){
		SqlUtil.SqlEntity entity = sqlutil.new SqlEntity();
		entity.setTable("clickstat");
		entity.addColumn("grade", request.getGrade()>0?"grade+1":"grade",false);
		entity.addColumn("gradeCount", request.getGrade()>0?"gradeCount+1":"gradeCount",false);
		entity.addColumn("support", request.getSupport()>0?"support+1":"support",false);
		entity.addColumn("oppo", request.getOppo()>0?"oppo+1":"oppo",false);
		entity.addWhereColumn("clickid", id);
		return sqlutil.generateUpdateSql(entity);
	}
}
