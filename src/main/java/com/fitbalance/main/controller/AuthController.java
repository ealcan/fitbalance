package com.fitbalance.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitbalance.main.Dto.UserDto;
import com.fitbalance.main.config.RegistrationResponse;
import com.fitbalance.main.entities.User;
import com.fitbalance.main.services.UserService;

/**
 * Controlador REST per gestionar l'autenticació i el registre d'usuaris.
 * Proporciona endpoints per al login i el registre d'usuaris.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/auth")
public class AuthController {

	/**
	 * Servei per gestionar operacions relacionades amb els usuaris.
	 */
	@Autowired
	private UserService userService;

	/**
	 * Endpoint per autenticar un usuari amb correu electrònic i contrasenya.
	 *
	 * @author Eric
	 * @param email    el correu electrònic de l'usuari.
	 * @param password la contrasenya de l'usuari.
	 * @return un objecte {@link ResponseEntity} que conté un {@link UserDto} amb el
	 *         nom d'usuari, el correu electrònic i el rol, si l'autenticació és
	 *         correcta. Retorna un codi d'estat 401 si l'autenticació falla.
	 */
	@PostMapping("/login")
	public ResponseEntity<UserDto> login(@RequestParam String email, @RequestParam String password) {
		User user = userService.authenticateByEmail(email, password); // Autenticar l'usuari per correu electrònic

		if (user != null) {
			String username = user.getUsername();
			String rol = user.getRol();
			return ResponseEntity.ok(new UserDto(username, email, rol)); // Retorna les dades de l'usuari autenticat
		}

		return ResponseEntity.status(401).body(null); // Retorna error si l'autenticació falla
	}

	/**
	 * Endpoint per registrar un nou usuari.
	 *
	 * @author eric
	 * @param password la contrasenya del nou usuari.
	 * @param email    el correu electrònic del nou usuari.
	 * @return un objecte {@link RegistrationResponse} amb el resultat del registre.
	 *         Retorna un missatge d'èxit si el registre és correcte o un missatge
	 *         d'error si el registre falla.
	 */
	@PostMapping("/register")
	public RegistrationResponse register(@RequestParam String password, @RequestParam String email) {
		try {
			userService.registerUser(password, email); // Registra un nou usuari
			return new RegistrationResponse(true, "Usuari registrat correctament");
		} catch (IllegalArgumentException e) {
			return new RegistrationResponse(false, e.getMessage()); // Retorna un error si el registre falla
		}
	}
}
