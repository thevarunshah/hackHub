package app;

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
    
    @RequestMapping("/register")
    public ResponseEntity register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password){
    	
    	if(organizers.containsKey(username)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Organizer organizer = new Organizer(username, password);
    	organizers.put(organizer.getUsername(), organizer);
    	return new ResponseEntity<Organizer>(organizer, HttpStatus.OK);
    }
    
    @RequestMapping("login")
    public ResponseEntity login(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password){
    	
    	if(organizers.containsKey(username)){
    		Organizer organizer = organizers.get(username);
    		
	    	if(organizer.getPassword().equals(password)){
	    		return new ResponseEntity<Organizer>(organizer, HttpStatus.OK);
	    	}
    	}
    	
    	return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    }

    @RequestMapping("/newHackathon")
    public Hackathon newHackathon(@RequestParam(value="name", required=true) String name, @RequestParam(value="location", required=true) String location,
    								@RequestParam(value="startDate", required=true) String startDate, @RequestParam(value="endDate", required=true) String endDate,
    								@RequestParam(value="organizer", required=true) String organizerUsername) {
    	
    	Organizer organizer = organizers.get(organizerUsername);
    	Hackathon hackathon = new Hackathon(hackathonsID.incrementAndGet(), name, location, startDate, endDate, organizer);
    	hackathons.put(hackathon.getId(), hackathon);
        return hackathon;
    }
    
    @RequestMapping("/getHackathon")
    public ResponseEntity getHackathon(@RequestParam(value="id", required=true) long id){
    	
    	if(hackathons.containsKey(id)){
    		return new ResponseEntity<Hackathon>(hackathons.get(id), HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    }
    
    @RequestMapping("updateHackathon")
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
    
    @RequestMapping("addAnouncement")
    public ResponseEntity addAnouncement(@RequestParam(value="id", required=true) long id, @RequestParam(value="anouncement", required=true) String anouncement){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getAnnouncements().add(anouncement);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    @RequestMapping("addEvent")
    public ResponseEntity addEvent(@RequestParam(value="id", required=true) long id, @RequestParam(value="event", required=true) String event){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getSchedule().add(event);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    @RequestMapping("addFAQ")
    public ResponseEntity addFAQ(@RequestParam(value="id", required=true) long id, @RequestParam(value="faq", required=true) String faq){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getFaqs().add(faq);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
    
    @RequestMapping("addMentor")
    public ResponseEntity addMentor(@RequestParam(value="id", required=true) long id, @RequestParam(value="mentor", required=true) String mentor){
    	
    	if(!hackathons.containsKey(id)){
    		return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    	}
    	
    	Hackathon hackathon = hackathons.get(id);
    	hackathon.getMentors().add(mentor);
    	return new ResponseEntity<Hackathon>(hackathon, HttpStatus.OK); 
    }
}
