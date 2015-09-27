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
    public ResponseEntity getHackathon(@RequestParam(value="id", required=true) long id) {
    	
    	if(hackathons.containsKey(id)){
    		return new ResponseEntity<Hackathon>(hackathons.get(id), HttpStatus.OK);
    	}
    	
    	return new ResponseEntity<EmptyJSONResponse>(new EmptyJSONResponse(), HttpStatus.OK);
    }
}
