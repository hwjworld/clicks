package config;

import java.util.ResourceBundle;


public class Config {
	private static Config config = null;
	
	int storetype = 1;
	//1 db
	private String driver = null; 
	private String url = null;
	private String un = null;
	private String pwd = null;
	//2 file
	private String filename = null;

	private int validNum=17;
	private int interval=3;
	private int timeRange=2*1000;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUn() {
		return un;
	}
	public void setUn(String un) {
		this.un = un;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public int getStoretype() {
		return storetype;
	}
	public void setStoretype(int storetype) {
		this.storetype = storetype;
	}
	
	public static Config getConfig(){
		if(config == null)
			loadConfig();
		return config;
	}
	private static void loadConfig() {
		config = new Config();
		ResourceBundle resource = ResourceBundle.getBundle("statConfig");
		config.driver = resource.getString("driver");
		config.url = resource.getString("url");
		config.un = resource.getString("username");
		config.pwd = resource.getString("password");
		config.validNum = Integer.parseInt(resource.getString("validNum"));
		config.interval = Integer.parseInt(resource.getString("interval"));
		config.timeRange = Integer.parseInt(resource.getString("timeRange"))*1000;
		System.out.println(resource.getString("timeRange"));
		config.storetype = Integer.parseInt(resource.getString("storetype"));
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public int getValidNum() {
		return validNum;
	}
	public void setValidNum(int validNum) {
		this.validNum = validNum;
	}
	public int getInterval() {
		return interval;
	}
	public void setInterval(int interval) {
		this.interval = interval;
	}
	public int getTimeRange() {
		return timeRange;
	}
	public void setTimeRange(int timeRange) {
		this.timeRange = timeRange;
	}
	
}
