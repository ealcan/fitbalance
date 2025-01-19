package com.fitbalance.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fitbalance.main.entities.Recipes;

/**
 * Repositorio para la entidad {@link Recipes}. Proporciona métodos para
 * realizar operaciones CRUD en la tabla de recetas.
 * 
 * @author Eric
 */
@Repository
public interface RecipesRepository extends CrudRepository<Recipes, Long> {

	/**
	 * Busca una receta por su nombre.
	 * 
	 * @param nom El nombre de la receta a buscar.
	 * @return La receta correspondiente al nombre proporcionado.
	 * @author Eric
	 */
	Recipes findByNom(String nom);

	/**
	 * Obtiene todas las recetas.
	 * 
	 * @return Una lista de todas las recetas en la base de datos.
	 * @author Eric
	 */
	List<Recipes> findAll();
}
