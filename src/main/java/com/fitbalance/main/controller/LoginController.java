package com.fitbalance.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin("*")
@Controller
public class LoginController {

	@GetMapping("/login")
	public String login() {
		return "login"; // Retorna la vista de login
	}

	@GetMapping("/logout")
	public String logout(Model model) {
		model.addAttribute("message", "Has cerrado sesión con éxito.");
		return "login"; // Retorna la vista de login
	}
}
