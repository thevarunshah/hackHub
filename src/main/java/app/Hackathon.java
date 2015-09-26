package app;

public class Hackathon {

    private final long id;
    private final String name;
    private String location;
    private String startDate;
    private String endDate;

    public Hackathon(long id, String name, String location, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
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

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}
}
