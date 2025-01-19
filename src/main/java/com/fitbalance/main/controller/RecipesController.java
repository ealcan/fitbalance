package com.fitbalance.main.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitbalance.main.entities.Ingredients;
import com.fitbalance.main.entities.Recipes;
import com.fitbalance.main.services.IngredientsService;
import com.fitbalance.main.services.RecipesServices;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestionar las recetas en el sistema. Proporciona
 * endpoints para listar todas las recetas y crear nuevas recetas.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/receptes")
@RequiredArgsConstructor
public class RecipesController {

	/**
	 * Servicio para gestionar las operaciones relacionadas con las recetas.
	 */
	@Autowired
	RecipesServices recipesService;

	/**
	 * Servicio para gestionar las operaciones relacionadas con los ingredientes.
	 */
	@Autowired
	IngredientsService ingredientsService;

	/**
	 * Obtiene todas las recetas registradas en el sistema.
	 *
	 * @author Eric
	 * @return una lista de objetos {@link Recipes} que representan todas las
	 *         recetas disponibles.
	 */
	@GetMapping("")
	public List<Recipes> getAll() {
		return recipesService.getAllRecipes();
	}

	/**
	 * Crea una nueva receta en el sistema. Valida los ingredientes de la receta y
	 * los asocia antes de guardar la receta.
	 *
	 * @author Eric
	 * @param recipe el objeto {@link Recipes} que representa la receta a crear.
	 * @return una respuesta HTTP con el objeto {@link Recipes} creado.
	 */
	@PostMapping("/new")
	public ResponseEntity<Recipes> crearReceta(@RequestBody Recipes recipe) {
		List<Ingredients> ingredientes = new ArrayList<>();

		// Validar y asociar los ingredientes proporcionados en la receta
		for (Ingredients ingredientRequest : recipe.getIngredientes()) {
			Ingredients ingrediente = ingredientsService.getIngredient(ingredientRequest.getNom());
			/*
			 * Si no se encuentra el ingrediente, puede lanzar una excepción o manejarlo
			 * adecuadamente
			 */
			// if (ingrediente == null) {
			// throw new ResourceNotFoundException("Ingrediente no encontrado: " +
			// ingredientRequest.getNom());
			// }
			ingredientes.add(ingrediente);
		}

		// Establecer los ingredientes validados en la receta
		recipe.setIngredientes(ingredientes);

		// Guardar la receta y devolverla en la respuesta
		Recipes nuevaReceta = recipesService.saveRecipe(recipe);
		return ResponseEntity.ok(nuevaReceta);
	}
}
