package com.fitbalance.main.controller;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fitbalance.main.entities.User;
import com.fitbalance.main.services.UserService;

/**
 * Clase de prueba para el controlador de autenticación. Utiliza pruebas de
 * integración con MockMvc para simular peticiones HTTP y validar respuestas.
 * 
 * @author Eric
 */
@WebMvcTest(AuthController.class)
@WithMockUser(username = "eric@example.com", authorities = { "admin", "ROLE_admin" })
public class AuthControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockController;

	/**
	 * Prueba de inicio de sesión. Simula una petición POST a la ruta "/auth/login"
	 * y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testLogin() throws Exception {
		User user = new User();
		when(userService.authenticateByEmail("eric@example.com", "0000")).thenReturn(user);
		this.mockController.perform(MockMvcRequestBuilders.post("/auth/login").with(csrf())
				.param("email", "eric@example.com").param("password", "0000"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Prueba de registro de usuario. Simula una petición POST a la ruta
	 * "/auth/register" y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testRegister() throws Exception {
		this.mockController.perform(MockMvcRequestBuilders.post("/auth/register").with(csrf())
				.param("email", "eric@example.com").param("password", "0000"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
