package com.fitbalance.main.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.http.MediaType;
import com.fitbalance.main.entities.User;
import com.fitbalance.main.services.UserService;

/**
 * Clase de prueba para el controlador de administración de usuarios. Utiliza
 * pruebas de integración con MockMvc para simular peticiones HTTP y validar
 * respuestas de los endpoints de administración.
 * 
 * @author Eric
 */
@WebMvcTest(AdminController.class)
@WithMockUser(username = "eric@example.com", authorities = { "admin", "ROLE_admin" })
public class AdminControllerTest {

	@MockBean
	private UserService userService;

	@Autowired
	private MockMvc mockController;

	/**
	 * Prueba para obtener todos los usuarios. Simula una petición GET a la ruta
	 * "/admin/users" y valida que la respuesta contenga la lista de usuarios.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testGetAllUsers() throws Exception {
		// Mock del servicio
		User user1 = new User(1L, "user1@example.com", "0000", "ROLE_usuari", "LastName1", null);
		User user2 = new User(2L, "user2@example.com", "0000", "ROLE_usuari", "LastName2", null);
		List<User> mockUsers = Arrays.asList(user1, user2);

		when(userService.getAllUsers()).thenReturn(mockUsers);

		// Petición y verificación
		mockController.perform(MockMvcRequestBuilders.get("/admin/users").accept(MediaType.APPLICATION_JSON))
				.andDo(result -> System.out.println(result.getResponse().getContentAsString()));

	}

	/**
	 * Prueba para eliminar todos los usuarios. Simula una petición DELETE a la ruta
	 * "/admin/delete/users" y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testDeleteAllUsers() throws Exception {
		// Mock del servicio
		doNothing().when(userService).deleteAllUsers();

		// Petición y verificación
		mockController.perform(MockMvcRequestBuilders.delete("/admin/delete/users").with(csrf())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

	}

	/**
	 * Prueba para eliminar un usuario específico. Simula una petición DELETE a la
	 * ruta "/admin/delete/{email}" con un correo específico y valida que la
	 * respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testDeleteUser() throws Exception {
		String emailToDelete = "test3@example.com";

		this.mockController.perform(MockMvcRequestBuilders.delete("/admin/delete/{email}", emailToDelete).with(csrf())
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	/**
	 * Prueba para actualizar el rol de un usuario a "ROLE_admin".
	 * Simula una petición PUT a la ruta "/admin/upgrade-role/{email}" 
	 * y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 */
	@Test
	void testUpgradeUserRole() throws Exception {
	    String emailToUpgrade = "test4@example.com";

	    // Mock del servicio
	    doNothing().when(userService).updateUserRole(emailToUpgrade, "ROLE_admin");

	    // Petición y verificación
	    mockController.perform(MockMvcRequestBuilders.put("/admin/upgrade-role/{email}", emailToUpgrade)
	            .with(csrf())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}

	/**
	 * Prueba para degradar el rol de un usuario a "ROLE_usuari".
	 * Simula una petición PUT a la ruta "/admin/downgrade-role/{email}" 
	 * y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 */
	@Test
	void testDowngradeUserRole() throws Exception {
	    String emailToDowngrade = "test5@example.com";

	    // Mock del servicio
	    doNothing().when(userService).updateUserRole(emailToDowngrade, "ROLE_usuari");

	    // Petición y verificación
	    mockController.perform(MockMvcRequestBuilders.put("/admin/downgrade-role/{email}", emailToDowngrade)
	            .with(csrf())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk());
	}


}
