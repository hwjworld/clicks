package requestReceiver;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import operation.ISaver;
import operation.IViewer;
import operation.RequestChecker;
import util.StringUtils;
import config.Config;
import entity.Request;

/**
 * dcs 顶clickstat
 * ccs 踩clickstat
 * xcs 星clickstat
 * @author Administrator
 *
 */
public class RequestReceiver extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8239559144209846794L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String ip = req.getRemoteHost();
		//id1,稿件的id
		String artid = req.getParameter("id");
		String op = req.getParameter("op");
		String yjvg = req.getParameter("yjvg");
		String cs = req.getParameter("cs");
		if(!(validParam(artid)&&validParam(op)&&validParam(yjvg)))
			return;
		if("a".equals(op))
			op = "1";
		else if("l".equals(op))
			op = "2";
		if(cs==null)cs="000";
		String result = "";
		Request request = getRequest(Integer.parseInt(op),artid,"",ip,cs);
		result  = processRequest(request);
		PrintWriter pw = resp.getWriter();
		pw.write(result);
		pw.flush();
		pw.close();
	}
	
	private String processRequest(Request request){
		if(request.getFun()==Request.OP_ADD){
			if(RequestChecker.addIPOneClick(request.getRequestip(), request.getArtid(), System.currentTimeMillis())){
				ISaver saver = OperationFactory.getSaver(Config.getConfig());
				saver.save(request);
			}
		}//else if(request.getFun() == Request.OP_LOOK){
		IViewer viewer = OperationFactory.getViewer(Config.getConfig());
		String ids[] = request.getArtid().split(";");
		List list = viewer.view(request);
		StringBuffer res = new StringBuffer();
		for(int i=0;i<ids.length;i++){
			Object[] obj = getObjectFromList(list,ids[i]);
			if(obj == null){
				appendResString("dcs"+ids[i], 0, res);
				appendResString("ccs"+ids[i], 0, res);
				appendResString("xcs"+ids[i], 0, res);
			}else{
				appendResString("dcs"+ids[i], StringUtils.getInt(obj[1].toString()), res);
				appendResString("ccs"+ids[i], StringUtils.getInt(obj[2].toString()), res);
				appendResString("xcs"+ids[i], StringUtils.getInt(obj[3].toString()), res);
			}
		}
		return res.toString();
	}
	
	private Object[] getObjectFromList(List list,String artid){
		Object [] obj = null;
		for(int i=0;i<list.size();i++){
			if(((Object[])list.get(i))[0].equals(artid)){
				obj = (Object[])list.get(i);
				break;
			}
		}
		return obj;
	}
	
	private void appendResString(String dcx,long rv,StringBuffer res) {
		res.append("try{");
		res.append("changeNum('"+dcx+"','"+rv+"');");
		res.append("}catch(e){}");
	}

	private Request getRequest(int op, String artid, String pageurl,String ip,String cs) {
		return new Request(op, artid, pageurl,ip,cs);
	}
	private boolean validParam(String p){
		if(p == null || p.length()==0)
			return false;
		else
			return true;
	}
}
