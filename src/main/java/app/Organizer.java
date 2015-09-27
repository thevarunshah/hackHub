package app;

import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Organizer {
	
	private final String username;
	@JsonIgnore
	private String password;
	private ArrayList<Hackathon> hackathons = new ArrayList<Hackathon>();
	
	public Organizer(String username, String password){
		
		this.username = username;
		this.password = password;
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

	public ArrayList<Hackathon> getHackathons() {
		return hackathons;
	}
}
