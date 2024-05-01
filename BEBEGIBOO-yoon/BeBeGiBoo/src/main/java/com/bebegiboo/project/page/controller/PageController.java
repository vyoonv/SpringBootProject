package com.bebegiboo.project.page.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("page")
public class PageController {

	@GetMapping("info")
	public String info() {
		return "page/info/page-info";
	}
	
}
