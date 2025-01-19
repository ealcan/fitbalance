package com.fitbalance.main.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fitbalance.main.entities.Ingredients;

/**
 * Repositorio para la entidad {@link Ingredients}.
 * Proporciona métodos para realizar operaciones CRUD en la tabla de ingredientes.
 * 
 * @author Eric
 */
@Repository
public interface IngredientsRepository extends CrudRepository<Ingredients, Long> {
    
    /**
     * Busca un ingrediente por su nombre.
     * 
     * @param nom El nombre del ingrediente a buscar.
     * @return El ingrediente correspondiente al nombre proporcionado.
     * @author Eric
     */
    Ingredients findByNom(String nom);
    
    /**
     * Obtiene todos los ingredientes.
     * 
     * @return Una lista de todos los ingredientes en la base de datos.
     * @author Eric
     */
    List<Ingredients> findAll();
}
