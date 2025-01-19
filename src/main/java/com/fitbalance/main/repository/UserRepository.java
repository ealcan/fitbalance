package com.fitbalance.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fitbalance.main.entities.Recipes;
import com.fitbalance.main.entities.User;

/**
 * Repositorio para la entidad {@link User}. Proporciona métodos para realizar
 * operaciones CRUD y consultas personalizadas en la tabla de usuarios.
 * 
 * @author Eric
 */
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * Busca un usuario por su nombre de usuario.
	 * 
	 * @param username El nombre de usuario a buscar.
	 * @return El usuario correspondiente al nombre de usuario proporcionado.
	 * @author Eric
	 */
	User findByUsername(String username);

	/**
	 * Busca un usuario por su correo electrónico.
	 * 
	 * @param email El correo electrónico a buscar.
	 * @return El usuario correspondiente al correo electrónico proporcionado.
	 * @author Eric
	 */
	User findByEmail(String email);

	/**
	 * Obtiene el menú de un usuario basado en su nombre de usuario.
	 * 
	 * @param username El nombre de usuario para obtener su menú.
	 * @return Una lista de recetas asociadas al usuario con el nombre de usuario
	 *         proporcionado.
	 * @author Eric
	 */
	@Query("SELECT u.menu FROM User u WHERE u.username = :username")
	List<Recipes> findMenuByUsername(@Param("username") String username);

	/**
	 * Obtiene todos los usuarios.
	 * 
	 * @return Una lista de todos los usuarios en la base de datos.
	 * @author Eric
	 */
	List<User> findAll();

	void deleteByEmail(String email);
}
