package org.fnec.tweb.onlineshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FnecApplication {

    @Autowired
    private ClientRepository repository;
    
	public static void main(String[] args) {
		SpringApplication.run(FnecApplication.class, args);
	}

}
