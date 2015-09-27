package app;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Organizer {
	
	private final String username;
	@JsonIgnore
	private String password;
	private final String name;
	private String email;
	
	public Organizer(String username, String password, String name, String email){
		
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}
}
