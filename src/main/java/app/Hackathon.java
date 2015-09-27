package app;

import java.util.ArrayList;

public class Hackathon {

    private final long id;
    private final String name;
    private String location;
    private String startDate;
    private String endDate;
    private final Organizer organizer;
    
    private String link = "";
    private String applicationOpenDate = "";
    private String description = "";
    private ArrayList<String> announcements = new ArrayList<String>();
    private ArrayList<String> schedule = new ArrayList<String>();
    private ArrayList<String> faq = new ArrayList<String>();
    private ArrayList<String> mentors = new ArrayList<String>();

    public Hackathon(long id, String name, String location, String startDate, String endDate, Organizer organizer) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizer = organizer;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Organizer getOrganizer() {
		return organizer;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getApplicationOpenDate() {
		return applicationOpenDate;
	}

	public void setApplicationOpenDate(String applicationOpenDate) {
		this.applicationOpenDate = applicationOpenDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<String> getAnnouncements() {
		return announcements;
	}

	public ArrayList<String> getSchedule() {
		return schedule;
	}

	public ArrayList<String> getFaq() {
		return faq;
	}

	public ArrayList<String> getMentors() {
		return mentors;
	}
}
