package org.fnec.tweb.onlineshop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class NewClientController {

	private static final Logger log = LoggerFactory.getLogger(NewClientController.class);

    @Autowired
    private ClientRepository repository;
    
	@PostMapping("/signup")
	public String newClient(
			@RequestParam(name="mail", required=false, defaultValue="") String mail,
			@RequestParam(name="firstName", required=false, defaultValue="") String firstName,
			@RequestParam(name="username", required=false, defaultValue="") String username, 
			@RequestParam(name="password", required=false, defaultValue="") String password, 
			@RequestParam(name="address", required=false, defaultValue="") String address,
			
			Model model)
	{
		
		repository.save(new Client(mail, firstName, username, password, address, "USER"));
		
		log.info("Customers found with findAll():");
		log.info("-------------------------------");
		for (Client aClient : repository.findAll()) {
			log.info(aClient.toString());
		}
		log.info("");
		
		model.addAttribute("mail", mail);
		model.addAttribute("firstName", firstName);
		model.addAttribute("username", username);
		model.addAttribute("password", password);
		model.addAttribute("address", address);

		return "new-client-view";
	}
}
