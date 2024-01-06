package com.example.demo.models;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity

public class Signup {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String username;
    public String email;
    public String mobile;
    public String password;
    public String reenterpasword;
	public String getReenterpasword() {
		return reenterpasword;
	}
	public void setReenterpasword(String reenterpasword) {
		this.reenterpasword = reenterpasword;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
