package com.gabriel.java_API.HomeController;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;


public class HomeController {
	
	
	@PostMapping("/processar")
	public String processarFormulario(@RequestParam String name, @RequestParam int age, Model model) {
		
		String str = "Agora foi";
		
		return str;
	}
}
