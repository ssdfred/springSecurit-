package fr.diginamic.dto;

import java.util.List;

public class UserAccountDto {

	private String username;
	private String password;
	private List<String> authorites;
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
	public List<String> getAuthorites() {
		return authorites;
	}
	public void setAuthorites(List<String> authorites) {
		this.authorites = authorites;
	}
	
}
