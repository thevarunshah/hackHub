package app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {
	
    private final AtomicLong hackathonsID = new AtomicLong();
    	
    private HashMap<String, Organizer> organizers = new HashMap<String, Organizer>();
    private HashMap<Long, Hackathon> hackathons = new HashMap<Long, Hackathon>();
    
    @RequestMapping("/demo")
    public EmptyJSONResponse demo(){
    	
    	Organizer demo = new Organizer("demo", "pass", "Varun Shah", "contact@hackru.org");
    	organizers.put("demo", demo);
    	
    	Hackathon hackRU = new Hackathon(hackathonsID.incrementAndGet(), "hackRU", "Rutgers", "10/03/15", "10/04/15", demo);
    	hackRU.setDescription("HackRU is a biannual hackathon at Rutgers University. You'll get free food, quality swag and meet awesome people. "
    			+ "It's the largest student-run educational event at Rutgers, and the second oldest student-run hackathon in the country. Come envision, build and demo your cool and original project!");
    	hackRU.getAnnouncements().add("registration closed!");
    	hackRU.getAnnouncements().add("get pumped!");
    	hackRU.getSchedule().add("Check-in for HackRU will be from 10am-12pm on Saturday morning.");
    	hackRU.getSchedule().add("The opening ceremony will begin at 12pm.");
    	hackRU.getSchedule().add("HackRU will end around 4pm on Sunday.");
    	hackRU.getFaqs().add("Who can attend? Current high school and university students can attend HackRU! All attendees are expected to adhere to the Major League Hacking Code of Conduct.");
    	hackRU.getFaqs().add("Will there be food? We will provide food from the beginning to the end. Everything will be provided by us for you.");
    	hackRU.getFaqs().add("I have another question! If you have any questions or concerns regarding HackRU contact us at team@hackru.org.");
    	hackRU.getMentors().add("Varun Shah, varun.shah@rutgers.edu");
    	hackRU.getMentors().add("Viral Jogani, viral.jogani@rutgers.edu");
    	hackathons.put(hackRU.getId(), hackRU);
    	
    	Hackathon hackNY = new Hackathon(hackathonsID.incrementAndGet(), "hackNY", "NYU", "09/26/15", "09/27/15", demo);
    	hackNY.setDescription("great hackathon");
    	hackNY.getAnnouncements().add("good luck!");
    	hackNY.getAnnouncements().add("dinner on the 13th floor!");
    	hackNY.getAnnouncements().add("insomnia cookies and tshirts - 13th floor!");
    	hackNY.getAnnouncements().add("blue hoodie - lost and found - claim it in 1302");
    	hackNY.getSchedule().add("12:00pm - Doors Open to Attendees; Lunch is Served (main hallway, outside Auditorium)");
    	hackNY.getSchedule().add("01:00pm - Opening Ceremonies, & NYC Startup API Presentations");
    	hackNY.getSchedule().add("03:00pm - Hacking Begins (see below for room list on Saturday)");
    	hackNY.getSchedule().add("03:30pm - Workshop: Hacker Orientation with Shy Ruparel, Major League Hacking (Room 312, see below for description)");
    	hackNY.getSchedule().add("04:30pm - Workshop: Introduction to Node.js with Justin Woo (Room 317, see below for description)");
    	hackNY.getSchedule().add("05:30pm - Workshop: Introduction to ReactJS with Sam Agnew (Room 312, see below for description)");
    	hackNY.getSchedule().add("06:00pm Ladies Storm Hackathons Meetup (Room 101)");
    	hackNY.getSchedule().add("07:30pm Dinner is Served (13th Floor Lounge)");
    	hackNY.getMentors().add("Shy Ruparel, Deputy Commissioner at Major League Hacking");
    	hackNY.getMentors().add("Cassidy Williams, Software Engineer & Developer Evangelist at Clarifai");
    	hackathons.put(hackNY.getId(), hackNY);
    	
    	return new EmptyJSONResponse();
    }
    
    /**
     * register an organizer
     * 
     * @param username username of the organizer
     * @param password password of the organizer
     * @param name name of the organizer
     * @param email email of the organizer
     * @return organizer info if successful, empty json if username already exists
     */
    @RequestMapping("/register")
    public ResponseEntity register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password,
    								@RequestParam(value="name", required=true) String name, @RequestParam(value="email", required=true) String email){
    	
    	if(organizers.containsKey(username)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Organizer organizer = new Organizer(username, password, name, email);
    	organizers.put(organizer.getUsername(), organizer);
    	return new ResponseEntity<Organizer>(organizer, HttpStatus.OK);
    }
    
    /**
     * login in an organizer
     * 
     * @param username username of the organizer
     * @param password password of the organizer
     * @return organizer info if successful, empty json if invalid username or password
     */
    @RequestMapping("/login")
    public ResponseEntity login(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password){
    	
    	if(organizers.containsKey(username)){
    		Organizer organizer = organizers.get(username);
    		
	    	if(organizer.getPassword().equals(password)){
	    		return new ResponseEntity<Organizer>(organizer, HttpStatus.OK);
	    	}
    	}
    	
    	return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    }
    
    /**
     * get all hackathons
     * 
     * @return all open hackathons
     */
    @RequestMapping("/allHackathons")
    public Collection<Hackathon> allHackathons(){
    	
    	return hackathons.values();
    }

    /**
     * register a new hackathon
     * 
     * @param name name of the hackathon
     * @param location location of the hackathon
     * @param startDate start date of the hackathon
     * @param endDate end date of the hackathon
     * @param organizerUsername username of the organizer creating this hackathon
     * @return hackathon info for the hackathon created
     */
    @RequestMapping("/newHackathon")
    public Hackathon newHackathon(@RequestParam(value="name", required=true) String name, @RequestParam(value="location", required=true) String location,
    								@RequestParam(value="startDate", required=true) String startDate, @RequestParam(value="endDate", required=true) String endDate,
    								@RequestParam(value="organizer", required=true) String organizerUsername) {
    	
    	Organizer organizer = organizers.get(organizerUsername);
    	Hackathon hackathon = new Hackathon(hackathonsID.incrementAndGet(), name, location, startDate, endDate, organizer);
    	hackathons.put(hackathon.getId(), hackathon);
        return hackathon;
    }
    
    /**
     * get information about a specific hackathon
     * 
     * @param id id of the hackathon
     * @return hackathon information if id exists, empty json if invalid id
     */
    @RequestMapping("/getHackathon")
    public ResponseEntity getHackathon(@RequestParam(value="id", required=true) long id){
    	
    	if(hackathons.containsKey(id)){
    		return new ResponseEntity<Hackathon>(hackathons.get(id), HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    }
    
    /**
     * update information about a hackathon
     * 
     * @param id id of the hackathon to be updated
     * @param location new location (optional)
     * @param startDate new start date (optional)
     * @param endDate new end date (optional)
     * @param link new link (optional)
     * @param applicationOpenDate new application open date (optional)
     * @param description new description (optional)
     * @return hacakthon information if updated, empty json if invalid id
     */
    @RequestMapping("/updateHackathon")
    public ResponseEntity updateHackathon(@RequestParam(value="id", required=true) long id,
    										@RequestParam(value="location") String location, @RequestParam(value="startDate") String startDate, @RequestParam(value="endDate") String endDate,
    										@RequestParam(value="link") String link, @RequestParam(value="applicationOpenDate") String applicationOpenDate, 
    										@RequestParam(value="description") String description){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	if(location != null){
    		hackathon.setLocation(location);
    	}
    	if(startDate != null){
    		hackathon.setStartDate(startDate);
    	}
    	if(endDate != null){
    		hackathon.setEndDate(endDate);
    	}
    	if(link != null){
    		hackathon.setLink(link);
    	}
    	if(applicationOpenDate != null){
    		hackathon.setApplicationOpenDate(applicationOpenDate);
    	}
    	if(description != null){
    		hackathon.setDescription(description);
    	}
    	
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    /**
     * get all announcements for a hackathon
     * 
     * @param id id of the hackathon
     * @return list of all announcements, empty json if invalid id
     */
    @RequestMapping("/getAnnouncements")
    public ResponseEntity getAnnouncements(@RequestParam(value="id", required=true) long id){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	return new ResponseEntity<ArrayList<String>>(hackathon.getAnnouncements(), HttpStatus.OK);
    }
    
    /**
     * add new announcement to hackathon
     * 
     * @param id if of the hackathon
     * @param announcement new announcement
     * @return hackathon information if successful, empty json if invalid id
     */
    @RequestMapping("/addAnnouncement")
    public ResponseEntity addAnnouncement(@RequestParam(value="id", required=true) long id, @RequestParam(value="announcement", required=true) String announcement){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getAnnouncements().add(announcement);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    /**
     * get the schedule of events for the hackathon
     * 
     * @param id id of the hackathon
     * @return schedule of events if successful, empty json if invalid id
     */
    @RequestMapping("/getSchedule")
    public ResponseEntity getSchedule(@RequestParam(value="id", required=true) long id){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	return new ResponseEntity<ArrayList<String>>(hackathon.getSchedule(), HttpStatus.OK);
    }
    
    /**
     * adding a new event to the schedule
     * 
     * @param id id of the hackathon
     * @param event event description
     * @return hackathon information if successful, empty json if invalid id
     */
    @RequestMapping("/addEvent")
    public ResponseEntity addEvent(@RequestParam(value="id", required=true) long id, @RequestParam(value="event", required=true) String event){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getSchedule().add(event);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    /**
     * get all of the FAQs for the hackathon
     * 
     * @param id id of the hackathon
     * @return all of the FAQs if successful, empty json if invalid id
     */
    @RequestMapping("/getFAQs")
    public ResponseEntity getFAQs(@RequestParam(value="id", required=true) long id){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	return new ResponseEntity<ArrayList<String>>(hackathon.getFaqs(), HttpStatus.OK);
    }
    
    /**
     * add a new FAQ to a hackathon
     * 
     * @param id id of the hackathon
     * @param faq new FAQ details
     * @return hacktahon information if successful, empty json if invalid id 
     */
    @RequestMapping("/addFAQ")
    public ResponseEntity addFAQ(@RequestParam(value="id", required=true) long id, @RequestParam(value="faq", required=true) String faq){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getFaqs().add(faq);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    /**
     * get list of mentors for the hackathon
     * 
     * @param id id of the hackathon
     * @return list of mentors if successful, empty json if invalid id
     */
    @RequestMapping("/getMentors")
    public ResponseEntity getMentors(@RequestParam(value="id", required=true) long id){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	return new ResponseEntity<ArrayList<String>>(hackathon.getMentors(), HttpStatus.OK);
    }
    
    /**
     * add a new mentor for this hackathon
     * 
     * @param id id of the hackathon
     * @param mentor information about the new mentor
     * @return hackathon information if successful, empty json if invalid id
     */
    @RequestMapping("/addMentor")
    public ResponseEntity addMentor(@RequestParam(value="id", required=true) long id, @RequestParam(value="mentor", required=true) String mentor){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getMentors().add(mentor);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
}
