package app;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HubController {
	
    private final AtomicLong counter = new AtomicLong();
    
    private HashMap<Long, Hackathon> hackathons = new HashMap<Long, Hackathon>();

    @RequestMapping("/newHackathon")
    public Hackathon newHackathon(@RequestParam(value="name", required=true) String name, @RequestParam(value="location", required=true) String location,
    								@RequestParam(value="startDate", required=true) String startDate, @RequestParam(value="endDate", required=true) String endDate) {
    	
    	Hackathon hackathon = new Hackathon(counter.incrementAndGet(), name, location, startDate, endDate);
    	hackathons.put(hackathon.getId(), hackathon);
        return hackathon;
    }
    
    @RequestMapping("/getHackathon")
    public Hackathon getHackathon(@RequestParam(value="id", required=true) long id) {
    	
        return hackathons.get(id);
    }
}
