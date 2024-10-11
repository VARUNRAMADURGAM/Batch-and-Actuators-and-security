package com.example.batch.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class DummyLearnController {

	@GetMapping("/welcome")
	public String getMsg() {
		
		String msg="Welcome to spring secutiry demo";
		
		return msg;
		
	}
	@GetMapping("/home")
	public String home() {
		
		String msg="Welcome to home";
		
		return msg;
		
	}
	@GetMapping("/balance")
	public String getBalance() {
		
		String msg="Your balance is rs10000000000";
		
		return msg;
		
	}
	@GetMapping("/statement")
	public String getStatement() {
		
		String msg="Statement is sent to your email id";
		
		return msg;
		
	}
	@GetMapping("/myloan")
	public String getMyLoan() {
		
		String msg="Your Loan amount due: 4,50,000rs";
		
		return msg;
		
	}
	@GetMapping("/contact")
	public String getContactUs() {
		
		String msg="+91-8892571789";
		
		return msg;
		
	}
}
