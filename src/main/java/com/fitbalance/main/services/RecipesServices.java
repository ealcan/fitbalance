package com.fitbalance.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitbalance.main.entities.Ingredients;
import com.fitbalance.main.entities.Recipes;
import com.fitbalance.main.repository.RecipesRepository;

/**
 * Servicio que gestiona las operaciones relacionadas con las recetas.
 * Proporciona métodos para obtener, guardar y calcular calorías de recetas.
 * 
 * @author Eric
 */
@Service
public class RecipesServices {

	@Autowired
	RecipesRepository recipesRepository;

	/**
	 * Obtiene todas las recetas almacenadas en la base de datos.
	 * 
	 * @return Una lista de todas las recetas.
	 * @author Eric
	 */
	public List<Recipes> getAllRecipes() {
		return recipesRepository.findAll();
	}

	/**
	 * Guarda una nueva receta en la base de datos.
	 * 
	 * @param recipe La receta a guardar.
	 * @return La receta guardada.
	 * @author Eric
	 */
	public Recipes saveRecipe(Recipes recipe) {
		return recipesRepository.save(recipe);
	}

	/**
	 * Busca una receta por su nombre.
	 * 
	 * @param nom El nombre de la receta a buscar.
	 * @return La receta correspondiente al nombre proporcionado.
	 * @author Eric
	 */
	public Recipes getRecipe(String nom) {
		return recipesRepository.findByNom(nom);
	}

	/**
	 * Calcula las calorías totales de una receta sumando las calorías de sus
	 * ingredientes.
	 * 
	 * @param recipe La receta de la que se calcularán las calorías.
	 * @return Las calorías totales de la receta.
	 * @author Eric
	 */
	public Double totalCalories(Recipes recipe) {
		Double caloriesTotals = recipe.getCaloriasTotals();
		List<Ingredients> ingredients = recipe.getIngredientes();
		for (Ingredients ingredientsRequest : ingredients) {
			caloriesTotals += ingredientsRequest.getCalories();
		}
		recipe.setCaloriasTotals(caloriesTotals);
		return caloriesTotals;
	}

}
