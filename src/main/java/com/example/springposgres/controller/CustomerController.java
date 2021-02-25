package com.example.springposgres.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.springposgres.model.Customer;
import com.example.springposgres.model.CustomerUI;
import com.example.springposgres.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
// import ch.qos.logback.core.ConsoleAppender;
// import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
// import ch.qos.logback.core.status.OnConsoleStatusListener;
// import ch.qos.logback.core.FileAppender;
 
 
@RestController
public class CustomerController {
	@Autowired
	CustomerRepository repository;
	public static final Logger logger = LoggerFactory.getLogger( "AnyUniqueStringHere" );
	//public static Logger logger = LoggerFactory.getLogger(CustomerController.class);
	public static void printLoggerState() {
        // print internal state
        LoggerContext lc = (LoggerContext)LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }
    String index(){
      logger.debug("This is a debug message");
      logger.info("This is an info message");
      logger.warn("This is a warn message");
      logger.error("This is an error message");
      new SpringLoggingHelper().helpMethod();
      return "index";
  }
	
	@GetMapping("/bulkcreate")
	public String bulkcreate(){
		// save a single Customer
		repository.save(new Customer("Rajesh", "Bhojwani"));
		
		// save a list of Customers
        repository.saveAll(Arrays.asList(new Customer("Salim", "Khan")
                       , new Customer("Rajesh", "Parihar")
                       , new Customer("Rahul", "Dravid")
                       , new Customer("Dharmendra", "Bhojwani")));
		
		return "Customers are created";
	}
	@PostMapping("/create")
	public String create(@RequestBody CustomerUI customer){
		// save a single Customer
		repository.save(new Customer(customer.getFirstName(), customer.getLastName()));

		return "Customer is created";
	}
	@GetMapping("/findall")
	public List<CustomerUI> findAll(){

		List<Customer> customers = repository.findAll();
		List<CustomerUI> customerUI = new ArrayList<>();
		
		for (Customer customer : customers) {
			customerUI.add(new CustomerUI(customer.getFirstName(),customer.getLastName()));
		}
		customerUI.add(new CustomerUI("Budi","Wijaya"));
		 return customerUI;
		//return null;
	}
	
	@RequestMapping("/search/{id}")
	public String search(@PathVariable long id){
		String customer = "";
		customer = repository.findById(id).toString();
		return customer;
	}
	
	@RequestMapping("/searchbyfirstname/{firstname}")
	public List<CustomerUI> fetchDataByLastName(@PathVariable String firstname){
	
		List<Customer> customers = repository.findByFirstName(firstname);
		List<CustomerUI> customerUI = new ArrayList<>();
		
		for (Customer customer : customers) {
			customerUI.add(new CustomerUI(customer.getFirstName(),customer.getLastName()));
		}

		return customerUI;
	}
	
	@PutMapping("/customer/{id}")
	public Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id2) {
		
		return repository.findById(id2)
		.map(customer -> {
			customer.setFirstName(newCustomer.getFirstName());
			customer.setLastName(newCustomer.getLastName());
			return repository.save(customer);
		})
		.orElseGet(() -> {
			newCustomer.setId(id2);
			return repository.save(newCustomer);
		});
	}
}