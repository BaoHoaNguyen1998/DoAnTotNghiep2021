package com.iuh.ABCStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoiController {

	@RequestMapping("/gio-hang-rong")
	public String loiGioHang(Model model) {
		return "gio-hang-rong";
	}
	
	@RequestMapping("/404")
	public String loi404(Model model) {
		return "404";
	}
}
