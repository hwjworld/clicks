package operation;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import util.StringUtils;
import database.ConnectionManager;
import database.DBUtil;
import database.SqlUtil;
import database.SqlUtil.SqlEntity;
import entity.Request;

public class DBViewer implements IViewer {
	private static SqlUtil sqlutil = new SqlUtil();
	
	public List view(Request request) {
		List rvlist = new ArrayList();
		Connection conn = null;
		try{
			conn = ConnectionManager.getInstance().getConnection();
			String sql = generateSelectSql(request);
			List<Object[]> list = DBUtil.getQueryList(sql, conn);
			for (int i=0;i<list.size();i++){
				Object[] tmp = list.get(i);
				Object [] rv = new Object[4];
				rv[0] = tmp[0];
				rv[1] = StringUtils.getInt(tmp[1].toString());
				rv[2] = StringUtils.getInt(tmp[2].toString());
				rv[3] = StringUtils.getInt(tmp[3].toString());
				rvlist.add(rv);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.closeAll(conn);
		}
		return rvlist;
	}

	private String generateSelectSql(Request request) {
		SqlEntity e = sqlutil.new SqlEntity();
		e.setTable("clickstat");
		e.addColumn("clickid", "4");
		e.addColumn("support", "4");
		e.addColumn("oppo", "3");
		e.addColumn("gradecount", "1");
		e.addColumn("grade", "6");
		e.addWhereColumn("clickid", "("+request.getArtid().replaceAll(";", ",")+")",false);
		return sqlutil.generateSelectSql(e);
	}
}
