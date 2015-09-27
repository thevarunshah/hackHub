package app;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {
	
    private final AtomicLong hackathonsID = new AtomicLong();
    
    private HashMap<String, Organizer> organizers = new HashMap<String, Organizer>();
    private HashMap<Long, Hackathon> hackathons = new HashMap<Long, Hackathon>();
    
    @RequestMapping("/register")
    public Organizer register(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password){
    	
    	if(organizers.containsKey(username)){
    		return null;
    	}
    	
    	Organizer organizer = new Organizer(username, password);
    	organizers.put(organizer.getUsername(), organizer);
    	return organizer;
    }
    
    @RequestMapping("login")
    public Organizer login(@RequestParam(value="username", required=true) String username, @RequestParam(value="password", required=true) String password){
    	
    	Organizer organizer = organizers.get(username);
    	if(organizer.getPassword().equals(password)){
    		return organizer;
    	}
    	
    	return null;
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
    public Hackathon getHackathon(@RequestParam(value="id", required=true) long id) {
    	
    	if(hackathons.containsKey(id)){
    		return hackathons.get(id);
    	}
    	
        return null;
    }
}
