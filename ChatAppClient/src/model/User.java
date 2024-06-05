package model;

public class User {
	private String tenHienThi;
	private String username;
	private String password;

	public User() {

	}

	public User(String tenHienThi, String username, String password) {
		this.tenHienThi = tenHienThi;
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTenHienThi() {
		return tenHienThi;
	}

	public void setTenHienThi(String tenHienThi) {
		this.tenHienThi = tenHienThi;
	}

}
