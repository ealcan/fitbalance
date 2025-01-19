package com.fitbalance.main.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitbalance.main.entities.Ingredients;
import com.fitbalance.main.entities.Recipes;
import com.fitbalance.main.entities.User;
import com.fitbalance.main.repository.RecipesRepository;
import com.fitbalance.main.repository.UserRepository;
import com.fitbalance.main.services.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/profile")
public class UserProfileController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@Autowired
	RecipesRepository recipesRepository;

	/**
	 * Obtiene el perfil del usuario autenticado.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * @return El objeto User con los detalles del perfil del usuario.
	 * @author Eric
	 */
	@GetMapping("")
	public User userProfile(Authentication authentication) {
		String username = authentication.getName(); // Obtiene el nombre del usuario autenticado
		User user = userRepository.findByUsername(username); // Agrega el nombre del usuario al modelo
		return user; // Retorna la vista del perfil del usuario
	}

	/**
	 * Endpoint para modificar los datos del usuario.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * @return El objeto User con los datos modificados.
	 * @author Eric
	 */
	@PutMapping("/updateData")
	public User updateUser(Authentication authentication, @RequestBody User updatedUserData) {
		String username = authentication.getName();
		// Llama al servicio para actualizar el usuario
		return userService.updateUser(username, updatedUserData);
	}

	/*
	 * /** Obtiene el menú del usuario autenticado.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * 
	 * @return Una lista de recetas disponibles para el usuario.
	 * 
	 * @author Eric
	 */
	@GetMapping("/menu")
	public List<Recipes> userMenu(Authentication authentication) {
		String username = authentication.getName();
		List<Recipes> menu = userRepository.findMenuByUsername(username);
		return menu;
	}

	/*
	 * /** Crea un menú aleatorio para el usuario autenticado y lo guarda en la base
	 * de datos.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * 
	 * @return Una lista de recetas generadas aleatoriamente para el usuario.
	 * 
	 * @author Eric
	 */
	@PostMapping("/menu/new")
	public List<Recipes> crearMenu(Authentication authentication) {
		String username = authentication.getName();

		User user = userRepository.findByUsername(username);
		user.getMenu().clear();

		List<Recipes> receptes = recipesRepository.findAll();

		List<Recipes> esmorzars = new ArrayList<>();
		List<Recipes> esmorzars2 = new ArrayList<>();
		List<Recipes> dinars = new ArrayList<>();
		List<Recipes> berenars = new ArrayList<>();
		List<Recipes> sopars = new ArrayList<>();

		for (Recipes recepta : receptes) {
			if ("Almuerzo".equalsIgnoreCase(recepta.getApat())) {
				esmorzars.add(recepta);
			} else if ("Comida".equalsIgnoreCase(recepta.getApat())) {
				dinars.add(recepta);
			} else if ("Merienda".equalsIgnoreCase(recepta.getApat())) {
				berenars.add(recepta);
			} else if ("Cena".equalsIgnoreCase(recepta.getApat())) {
				sopars.add(recepta);
			} else if ("Desayuno".equalsIgnoreCase(recepta.getApat())) {
				esmorzars2.add(recepta);
			}
		}

		Collections.shuffle(dinars);
		Collections.shuffle(sopars);
		Collections.shuffle(berenars);
		Collections.shuffle(esmorzars);
		Collections.shuffle(esmorzars2);
		List<Recipes> menu = new ArrayList<>();
		List<Recipes> menu2 = new ArrayList<>();
		menu.addAll(dinars.subList(0, 7));
		menu.addAll(esmorzars.subList(0, 7));
		menu.addAll(esmorzars2.subList(0, 7));
		menu.addAll(sopars.subList(0, 7));
		menu.addAll(berenars.subList(0, 7));

		user.setMenu(menu);
		userRepository.save(user);

		return menu;
	}
	
	/**
	 * Endpoint para borrar el menú del usuario autenticado.
	 * 
	 * Este método limpia el menú asociado al usuario autenticado.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * @return Una respuesta indicando el éxito o el fallo de la operación.
	 */
	@DeleteMapping("/menu/clear")
	public ResponseEntity<String> borrarMenu(Authentication authentication) {
	    String username = authentication.getName();

	    // Busca el usuario autenticado
	    User user = userRepository.findByUsername(username);
	    if (user == null) {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
	    }

	    // Limpia el menú del usuario
	    user.getMenu().clear();
	    userRepository.save(user);

	    return ResponseEntity.ok("Menú borrado con éxito");
	}

	
	/**
	 * Endpoint para generar una lista de la compra basada en el menú del usuario autenticado.
	 * 
	 * Este método crea una lista de ingredientes necesarios para las recetas del menú del usuario.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * @return Un conjunto de ingredientes únicos necesarios para el menú del usuario.
	 */
	@PostMapping("/shopping-list/new")
	public Set<Ingredients> generarListaDeLaCompra(Authentication authentication) {
	    String username = authentication.getName();
	    User user = userRepository.findByUsername(username);

	    List<Recipes> menu = user.getMenu();
	    Set<Ingredients> listaCompra = new HashSet<>();  // Usamos un HashSet para evitar duplicados

	    // Iteramos sobre las recetas del menú
	    for (Recipes receta : menu) {
	        List<Ingredients> ingredientes = receta.getIngredientes();

	        // Iteramos sobre los ingredientes de cada receta
	        for (Ingredients ingredient : ingredientes) {
	            if (ingredient.getNom() != null && !ingredient.getNom().isEmpty()) {
	                // Si el nombre del ingrediente no es null ni vacío, lo agregamos al Set
	                listaCompra.add(ingredient);
	            } else {
	                // Si el nombre es null, logueamos o manejamos el caso según tu necesidad
	                System.out.println("Ingrediente sin nombre encontrado: " + ingredient);
	            }
	        }
	    }

	    return listaCompra;
	}

	/**
	 * Endpoint para ver la lista de la compra generada previamente para el usuario autenticado.
	 * 
	 * Este método devuelve la lista de ingredientes única, si el usuario tiene un menú asociado.
	 * 
	 * @param authentication Información de autenticación del usuario.
	 * @return Un conjunto de ingredientes necesarios para las recetas del menú.
	 */
	@GetMapping("/shopping-list")
	public Set<Ingredients> verListaDeLaCompra(Authentication authentication) {
	    String username = authentication.getName();
	    User user = userRepository.findByUsername(username);

	    // Obtener la lista de la compra generada previamente
	    Set<Ingredients> shoppingList = new HashSet<>();  // Cambiar a HashSet

	    // Si el usuario tiene un menú, generamos la lista de la compra
	    if (user.getMenu() != null && !user.getMenu().isEmpty()) {
	        shoppingList = generarListaDeLaCompra(authentication);  // Usamos el método anterior
	    } else {
	        // En caso de que el usuario no tenga un menú, retornamos un conjunto vacío
	        return shoppingList;  // Retorna un conjunto vacío si no hay menú
	    }

	    // Retorna la lista de la compra generada
	    return shoppingList;
	}


}
