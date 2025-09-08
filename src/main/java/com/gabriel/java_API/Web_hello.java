package com.gabriel.java_API;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Web_hello {

	@GetMapping("/hello")
	public String hello() {
		Aluno aluno1 = new Aluno ("Gabriel", 26);
		String myName = aluno1.getName();
		Integer myAge = aluno1.getAge();
		return  "Hello " + myName + " I'm spring boot and i now that you have " + myAge + " years";
	}
	
}
