package entity;

public final class Request {
	public static final int OP_ADD = 1;
	public static final int OP_LOOK = 2;
	
	/**
	 * 1:add 2:look
	 */
	int fun = 0;
	String requestip;
	String artid;
	String pageurl;
	
	long support;
	long oppo;
	long grade;
	int gradecount;
	
	public Request(int fun,String artid,String pageurl, String ip,String cs) {
		this.fun = fun;
		if(artid.endsWith(";")){
			this.artid = artid.substring(0,artid.length()-1);
		}else{
			this.artid = artid;
		}
		this.pageurl = pageurl;
		this.requestip = ip;
		this.support = cs.charAt(0)>'0'?1:0;
		this.oppo = cs.charAt(1)>'0'?1:0;
		this.grade = cs.charAt(2)>'0'?1:0;
		if(this.grade == 1){
			this.gradecount = 1;
		}
	}
	
	public int getFun() {
		return fun;
	}
	public String getArtid() {
		return artid;
	}
	public String getPageurl() {
		return pageurl;
	}

	public long getSupport() {
		return support;
	}

	public void setSupport(long support) {
		this.support = support;
	}

	public long getOppo() {
		return oppo;
	}

	public void setOppo(long oppo) {
		this.oppo = oppo;
	}

	public long getGrade() {
		return grade;
	}

	public void setGrade(long grade) {
		this.grade = grade;
	}

	public String getRequestip() {
		return requestip;
	}

	public void setRequestip(String requestip) {
		this.requestip = requestip;
	}

	public int getGradecount() {
		return gradecount;
	}

	public void setGradecount(int gradecount) {
		this.gradecount = gradecount;
	}

	public static void main(String[] args) {
		String str = "100";
		System.out.print(str.charAt(2)>'0');
	}	
}
