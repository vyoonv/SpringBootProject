package edu.kh.project.business.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FinalMainController {
	
	@GetMapping("final/main")
	public String finalMain() {
		
		return "final/main"; 
	}

}
