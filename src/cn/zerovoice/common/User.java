package cn.zerovoice.common;

public class User implements java.io.Serializable{
	String operation;
	int account;
	String password;
	String nick;
	String avatar;
	int avatarTag;
	String trends;
	String sex;
	int age;
	int lev;
	String time;
	
	
	
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public int getAccount() {
		return account;
	}
	public void setAccount(int account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public int getTag() {
		return avatarTag;
	}
	public void setTag(int tag) {
		this.avatarTag = tag;
	}
	public String getTrends() {
		return trends;
	}
	public void setTrends(String trends) {
		this.trends = trends;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getLev() {
		return lev;
	}
	public void setLev(int lev) {
		this.lev = lev;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	

}
