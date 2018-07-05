package ash.boot.mvcexample.controllers;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ash.boot.mvcexample.models.Person;
import ash.boot.mvcexample.services.PersonRepository;

@Controller    // This means that this class is a Controller
//@RequestMapping(path="/api") 
public class MainController {
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	private PersonRepository personRepository;
	
	@ResponseBody
    @RequestMapping("/knockknock")
    public Person knockknock(@RequestParam(value="first") String first,
    		@RequestParam(value="last") String last
    		) {
    	System.out.println("knockknock: " +first +" "+ last);
    	if(first.isEmpty()) {
    		System.out.println("please add ?first=first&last=last to your request");
    		return new Person("Who's","There");
    		
    	}
    	else {
    		//TODO add query for username and give back first and last name
    		return new Person(first,last);
    	}
        
    }
	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (
			@RequestParam String firstName, 
			@RequestParam String lastName) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request

		Person n = new Person();
		n.setFirstName(firstName);
		n.setLastName(lastName);
		personRepository.save(n);
		return "Saved";
	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Person> getAllUsers() {
		// This returns a JSON or XML with the users
		return personRepository.findAll();
	}
	
	@GetMapping("/greet")
    public String greeting(@RequestParam(name="name", 
	    	required=false, 
	    	defaultValue="World") String name, 
	    	Model model) {
    	System.out.println("MainCOntroller");

        model.addAttribute("name", name);
        return "greet";
    }
    
    @GetMapping("/ajax")
    public String ajax(Model model) {
    	System.out.println("MainCOntroller");
    	return "ajaxCSRF";
    	//return "ajaxIndex";//seemed to need to have csrf
    }
}