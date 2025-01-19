package com.fitbalance.main.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fitbalance.main.entities.Ingredients;
import com.fitbalance.main.services.IngredientsService;
import com.google.gson.Gson;

/**
 * Clase de prueba para el controlador de ingredientes. Utiliza pruebas de
 * integración con MockMvc para simular peticiones HTTP y validar respuestas.
 * 
 * @author Eric
 */
@WebMvcTest(IngredientsController.class)
@WithMockUser(username = "eric@example.com", authorities = { "admin", "ROLE_admin" })
class IntedientsConstrollerTest {

	@MockBean
	IngredientsService ingredientsService;

	@Autowired
	private MockMvc mockController;

	/**
	 * Prueba para obtener todos los ingredientes. Simula una petición GET a la ruta
	 * "/ingredients" y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testGetAllIngredients() throws Exception {
		this.mockController.perform(MockMvcRequestBuilders.get("/ingredients"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Prueba para crear un nuevo ingrediente. Simula una petición POST a la ruta
	 * "/ingredients/new" con un ingrediente en formato JSON, y valida que la
	 * respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testCrearIngredients() throws Exception {
		Ingredients ingredients = new Ingredients();
		Gson obj = new Gson();
		String request = obj.toJson(ingredients);
		this.mockController
				.perform(MockMvcRequestBuilders.post("/ingredients/new").with(csrf())
						.contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
