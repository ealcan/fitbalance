package com.fitbalance.main.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.fitbalance.main.entities.User;
import com.fitbalance.main.repository.RecipesRepository;
import com.fitbalance.main.repository.UserRepository;
import com.fitbalance.main.services.UserService;

/**
 * Clase de prueba para el controlador de perfil de usuario. Utiliza pruebas de
 * integración con MockMvc para simular peticiones HTTP y validar respuestas.
 * 
 * @author Eric
 */
@WebMvcTest(UserProfileController.class)
@WithMockUser(username = "eric@example.com", authorities = { "admin", "ROLE_admin" })
public class UserProfileControllerTest {

	@MockBean
	private UserService userService;

	@MockBean
	UserRepository userRepository;

	@MockBean
	RecipesRepository recipesRepository;

	@Autowired
	private MockMvc mockController;

	/**
	 * Prueba para obtener el perfil de un usuario. Simula una petición GET a la
	 * ruta "/profile" pasando el parámetro "username" con el valor
	 * "eric@example.com", y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testGetProfile() throws Exception {
		this.mockController.perform(MockMvcRequestBuilders.get("/profile").param("username", "eric@example.com"))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	/**
	 * Prueba para modificar el perfil de un usuario. Creamos un usuario para hacer
	 * la prueba Simula una petición POST a la ruta "/profile" pasando el parámetro
	 * el usuario creado, y valida que la respuesta sea OK.
	 * 
	 * @throws Exception Si ocurre algún error durante la ejecución de la prueba.
	 * @author Eric
	 */
	@Test
	void testUpdateUser() throws Exception {
		// Crea un objeto User con los nuevos datos que deseas enviar
		User updatedUser = new User(1L, "eric@example.com", "newpassword", "ROLE_admin", "NewLastName", null);

		// Simula la actualización del usuario, haciendo que el servicio retorne el
		// usuario actualizado
		when(userService.updateUser(eq("eric@example.com"), any(User.class))).thenReturn(updatedUser);

		// Realiza la solicitud PUT al endpoint /updateData con el usuario actualizado
		// en formato JSON

		this.mockController.perform(MockMvcRequestBuilders.put("/updateData").contentType("application/json").content(
				"{\"id\": 1, \"email\": \"eric@example.com\", \"password\": \"newpassword\", \"role\": \"ROLE_admin\", \"lastName\": \"NewLastName\"}")
				.with(csrf())).andReturn().getResponse().getContentAsString();

		// Verifica que el servicio 'updateUser' fue llamado con el usuario adecuado
		// verify(userService, times(1)).updateUser(eq("eric@example.com"),
		// any(User.class));
	}
	
	@Test
	void testVerListaDeLaCompra() throws Exception {
	    // Crear un usuario con su receta y su lista de ingredientes
	    User mockUser = new User(1L, "eric@example.com", "password", "ROLE_user", "LastName", new ArrayList<>());
	    Recipes mockRecipe = new Recipes(null, "Tortilla", "Cena", Double.valueOf(200), new ArrayList<>());
	    Ingredients mockIngredient = new Ingredients(); // Suponiendo que 'nom' es el campo para el nombre del ingrediente
	    mockIngredient.setNom("Ingrediente 1"); // Establecer el nombre del ingrediente

	    mockRecipe.setIngredientes(List.of(mockIngredient));
	    mockUser.setMenu(List.of(mockRecipe));

	    // Configura el comportamiento del repositorio simulado
	    when(userRepository.findByUsername("eric@example.com")).thenReturn(mockUser);

	    // Realiza la llamada GET al endpoint "/profile/shopping-list"
	    this.mockController.perform(MockMvcRequestBuilders.get("/profile/shopping-list")
	            .with(csrf())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            // Asegúrate de acceder al campo correcto 'nom' para el ingrediente
	            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Ingrediente 1"));
	}

	
	@Test
	void testGenerarListaDeLaCompra() throws Exception {
	    // Crear un usuario con su receta y su lista de ingredientes
	    User mockUser = new User(1L, "eric@example.com", "password", "ROLE_user", "LastName", new ArrayList<>());
	    Recipes mockRecipe = new Recipes(null, "Tortilla", "Cena", Double.valueOf(200), new ArrayList<>());
	    Ingredients mockIngredient = new Ingredients(); // Suponiendo que 'nom' es el campo para el nombre del ingrediente
	    mockIngredient.setNom("Ingrediente 1"); // Establecer el nombre del ingrediente

	    mockRecipe.setIngredientes(List.of(mockIngredient));
	    mockUser.setMenu(List.of(mockRecipe));

	    // Configura el comportamiento del repositorio simulado
	    when(userRepository.findByUsername("eric@example.com")).thenReturn(mockUser);

	    // Realiza la llamada POST al endpoint "/profile/shopping-list/new"
	    mockController.perform(MockMvcRequestBuilders.post("/profile/shopping-list/new")
	            .with(csrf())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            // Asegúrate de acceder al campo correcto 'nom' para el ingrediente
	            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Ingrediente 1"));
	}


	
	@Test
	void testBorrarMenu() throws Exception {
	    User mockUser = new User(1L, "eric@example.com", "password", "ROLE_user", "LastName", new ArrayList<>());

	    when(userRepository.findByUsername("eric@example.com")).thenReturn(mockUser);

	    mockController.perform(MockMvcRequestBuilders.delete("/profile/menu/clear")
	            .with(csrf())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.content().string("Menú borrado con éxito"));
	}

	
	@Test
	void testUserMenu() throws Exception {
	    // Simula un menú con dos recetas
	    List<Recipes> mockMenu = List.of(
	        new Recipes(1L, "Receta 1", "Almuerzo", 500.0, new ArrayList<>()),
	        new Recipes(2L, "Receta 2", "Cena", 300.0, new ArrayList<>())
	    );

	    // Configura el comportamiento del repositorio simulado
	    when(userRepository.findMenuByUsername("eric@example.com")).thenReturn(mockMenu);

	    // Realiza la llamada GET al endpoint "/profile/menu"
	    mockController.perform(MockMvcRequestBuilders.get("/profile/menu")
	            .with(csrf())
	            .accept(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            // Asegúrate de que se esté usando el campo correcto: "nom"
	            .andExpect(MockMvcResultMatchers.jsonPath("$[0].nom").value("Receta 1"))
	            .andExpect(MockMvcResultMatchers.jsonPath("$[1].nom").value("Receta 2"));
	}

	
	@Test
	void testCrearMenu() throws Exception {
	    // Crear un usuario mock
	    User mockUser = new User(1L, "eric@example.com", "password", "ROLE_user", "LastName", new ArrayList<>());

	    // Crear una lista de recetas simuladas para cada tipo de comida (Almuerzo, Comida, Cena, Merienda, Desayuno)
	    List<Recipes> mockRecipes = new ArrayList<>();

	    for (int i = 0; i < 7; i++) {
	        // Agregar recetas para "Almuerzo"
	        mockRecipes.add(new Recipes(null, "Tortilla Almuerzo " + i, "Almuerzo", Double.valueOf(200), new ArrayList<>()));
	        // Agregar recetas para "Comida"
	        mockRecipes.add(new Recipes(null, "Tortilla Comida " + i, "Comida", Double.valueOf(200), new ArrayList<>()));
	        // Agregar recetas para "Cena"
	        mockRecipes.add(new Recipes(null, "Tortilla Cena " + i, "Cena", Double.valueOf(200), new ArrayList<>()));
	        // Agregar recetas para "Merienda"
	        mockRecipes.add(new Recipes(null, "Tortilla Merienda " + i, "Merienda", Double.valueOf(200), new ArrayList<>()));
	        // Agregar recetas para "Desayuno"
	        mockRecipes.add(new Recipes(null, "Tortilla Desayuno " + i, "Desayuno", Double.valueOf(200), new ArrayList<>()));
	    }

	    // Mockear la búsqueda del usuario y la recuperación de todas las recetas
	    when(userRepository.findByUsername("eric@example.com")).thenReturn(mockUser);
	    when(recipesRepository.findAll()).thenReturn(mockRecipes);

	    // Ejecutar el test
	    mockController.perform(MockMvcRequestBuilders.post("/profile/menu/new")
	            .with(csrf())
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(MockMvcResultMatchers.status().isOk())
	            .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(35))  // Verificar que el tamaño del menú es 35 (7 por cada tipo)
	            .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.nom == 'Tortilla Almuerzo 0')]").exists())  // Verificar que al menos existe una receta de almuerzo
	            .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.nom == 'Tortilla Comida 0')]").exists())  // Verificar que al menos existe una receta de comida
	            .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.nom == 'Tortilla Cena 0')]").exists())  // Verificar que al menos existe una receta de cena
	            .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.nom == 'Tortilla Merienda 0')]").exists())  // Verificar que al menos existe una receta de merienda
	            .andExpect(MockMvcResultMatchers.jsonPath("$[?(@.nom == 'Tortilla Desayuno 0')]").exists());  // Verificar que al menos existe una receta de desayuno
	}




}
