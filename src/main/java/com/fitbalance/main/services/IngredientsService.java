package com.fitbalance.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitbalance.main.entities.Ingredients;
import com.fitbalance.main.repository.IngredientsRepository;

/**
 * Servicio que gestiona las operaciones relacionadas con los ingredientes.
 * Proporciona métodos para obtener, guardar y buscar ingredientes.
 * 
 * @author Eric
 */
@Service
public class IngredientsService {

	@Autowired
	IngredientsRepository ingredientsRepository;

	/**
	 * Obtiene todos los ingredientes almacenados en la base de datos.
	 * 
	 * @return Una lista de todos los ingredientes.
	 * @author Eric
	 */
	public List<Ingredients> getAllIngredients() {
		return ingredientsRepository.findAll();
	}

	/**
	 * Guarda un nuevo ingrediente en la base de datos.
	 * 
	 * @param ingredient El ingrediente a guardar.
	 * @return El ingrediente guardado.
	 * @author Eric
	 */
	public Ingredients saveIngredient(Ingredients ingredient) {
		return ingredientsRepository.save(ingredient);
	}

	/**
	 * Busca un ingrediente por su nombre.
	 * 
	 * @param nom El nombre del ingrediente a buscar.
	 * @return El ingrediente correspondiente al nombre proporcionado.
	 * @author Eric
	 */
	public Ingredients getIngredient(String nom) {
		return ingredientsRepository.findByNom(nom);
	}

}
