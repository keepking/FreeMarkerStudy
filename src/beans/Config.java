package beans;

import java.io.Serializable;

public class Config implements Serializable{

	public String appName;
	public String dir;
	public String type;
	public String applicationPackage;

	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getApplicationPackage() {
		return applicationPackage;
	}
	public void setApplicationPackage(String applicationPackage) {
		this.applicationPackage = applicationPackage;
	}


}
