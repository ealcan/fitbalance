package com.fitbalance.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitbalance.main.entities.Ingredients;
import com.fitbalance.main.services.IngredientsService;

import lombok.RequiredArgsConstructor;

/**
 * Controlador REST para gestionar los ingredientes en el sistema. Permite
 * realizar operaciones CRUD relacionadas con los ingredientes.
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/ingredients")
@RequiredArgsConstructor
public class IngredientsController {

	/**
	 * Servicio para gestionar los ingredientes.
	 */
	@Autowired
	IngredientsService ingredientsService;

	/**
	 * Obtiene todos los ingredientes registrados en el sistema.
	 *
	 * @author Eric
	 * @return una lista de todos los ingredientes disponibles.
	 */
	@GetMapping("")
	public List<Ingredients> getAll() {
		return ingredientsService.getAllIngredients();
	}

	/**
	 * Crea un nuevo ingrediente en el sistema.
	 *
	 * @author Eric
	 * @param ingredient el objeto {@link Ingredients} que representa el nuevo
	 *                   ingrediente a crear.
	 * @return el ingrediente creado.
	 */
	@PostMapping("/new")
	public Ingredients nouIngredient(@RequestBody Ingredients ingredient) {
		return ingredientsService.saveIngredient(ingredient);
	}
}
