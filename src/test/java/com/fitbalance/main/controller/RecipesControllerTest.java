package com.fitbalance.main.controller;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import java.util.ArrayList;
import java.util.List;

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
import com.fitbalance.main.entities.Recipes;
import com.fitbalance.main.services.IngredientsService;
import com.fitbalance.main.services.RecipesServices;
import com.google.gson.Gson;

/**
 * Clase de prueba para el controlador de recetas. Utiliza pruebas de
 * integración con MockMvc para simular peticiones HTTP y validar respuestas.
 * 
 * @author Eric
 */
@WebMvcTest(RecipesController.class)
@WithMockUser(username = "eric@example.com", authorities = { "admin", "ROLE_admin" })
class RecipesControllerTest {

	@MockBean
	RecipesServices recipesService;

	@MockBean
	IngredientsService ingredientsService;

	@Autowired
	private MockMvc mockController;

	/**
	 * Prueba para obtener todas las recetas. Simula una petición GET a la ruta
	 * "/receptes" y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testGetAll() throws Exception {
		List<Recipes> results = new ArrayList<Recipes>();
		when(recipesService.getAllRecipes()).thenReturn(results);
		this.mockController.perform(MockMvcRequestBuilders.get("/receptes"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Prueba para crear una nueva receta. Simula una petición POST a la ruta
	 * "/receptes/new" con una receta en formato JSON, y valida que la respuesta sea
	 * OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testCrearReceta() throws Exception {
		Recipes recipe = new Recipes(null, "Tortilla", "Cena", Double.valueOf(200), new ArrayList<Ingredients>());
		Gson obj = new Gson();
		String request = obj.toJson(recipe);
		this.mockController
				.perform(MockMvcRequestBuilders.post("/receptes/new").with(csrf())
						.contentType(MediaType.APPLICATION_JSON).content(request))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
