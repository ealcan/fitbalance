package com.fitbalance.main.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitbalance.main.entities.Recipes;
import com.fitbalance.main.entities.User;
import com.fitbalance.main.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios,
 * incluyendo autenticación, registro y obtención de información.
 * 
 * @author Eric
 */
@Service
public class UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	/**
	 * Obtiene todos los usuarios de la base de datos.
	 * 
	 * @return Lista de usuarios.
	 * @author Eric
	 */
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	/**
	 * Guarda un nuevo usuario en la base de datos.
	 * 
	 * @param user El usuario a guardar.
	 * @return El usuario guardado.
	 * @author Eric
	 */
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	/**
	 * Obtiene las recetas de un usuario por su nombre de usuario.
	 * 
	 * @param username El nombre de usuario del cual obtener las recetas.
	 * @return Lista de recetas asociadas al usuario.
	 * @author Eric
	 */
	public List<Recipes> getRecipes(String username) {
		return userRepository.findMenuByUsername(username);
	}

	/**
	 * Autentica a un usuario por su nombre de usuario y contraseña.
	 * 
	 * @param username El nombre de usuario.
	 * @param password La contraseña del usuario.
	 * @return El usuario autenticado si las credenciales son correctas, de lo
	 *         contrario, null.
	 * @author Eric
	 */
	public User authenticate(String username, String password) {
		// Buscar el usuario en la base de datos por el nombre de usuario
		var user = userRepository.findByUsername(username);

		// Si el usuario existe y la contraseña es correcta, devolver el objeto User
		if (user != null && passwordEncoder.matches(password, user.getPassword_hash())) {
			return user; // Devuelve el objeto User si las credenciales son válidas
		}

		// Si no es válido, devuelve null
		return null;
	}

	/**
	 * Autentica a un usuario por su correo electrónico y contraseña.
	 * 
	 * @param email    El correo electrónico del usuario.
	 * @param password La contraseña del usuario.
	 * @return El usuario autenticado si las credenciales son correctas, de lo
	 *         contrario, null.
	 * @author Eric
	 */
	public User authenticateByEmail(String email, String password) {
		var user = userRepository.findByEmail(email); // Busca el usuario por email en lugar de username

		if (user != null && passwordEncoder.matches(password, user.getPassword_hash())) {
			return user; // Devuelve el usuario si las credenciales son válidas
		}
		return null; // Si las credenciales no son válidas, devuelve null
	}

	/**
	 * Registra un nuevo usuario en el sistema. Si el correo electrónico ya está
	 * registrado, lanza una excepción.
	 * 
	 * @param password La contraseña del nuevo usuario.
	 * @param email    El correo electrónico del nuevo usuario.
	 * @return El usuario registrado.
	 * @throws IllegalArgumentException Si el usuario ya existe.
	 * @author Eric
	 */
	public User registerUser(String password, String email) {
		if (userRepository.findByEmail(email) != null) {
			throw new IllegalArgumentException("El usuario ya existe");
		}

		User user = new User();
		user.setEmail(email);
		user.setPassword_hash(passwordEncoder.encode(password));
		user.setRol("ROLE_usuari");

		return userRepository.save(user);
	}

	@Transactional
	public void deleteUser(String email) {
		userRepository.deleteByEmail(email);
	}

	@Transactional
	public void deleteAllUsers() {
		userRepository.deleteAll();
	}

	@Transactional
	public User updateUser(String username, User updatedUserData) {
		// Buscar el usuario por email
		User existingUser = userRepository.findByUsername(username);

		// Actualizar los datos necesarios
		existingUser.setUsername(updatedUserData.getUsername());
		existingUser.setPassword_hash(passwordEncoder.encode(updatedUserData.getPassword_hash()));
		existingUser.setEmail(updatedUserData.getEmail());
		// Agregar más campos según sea necesario

		// Guardar el usuario actualizado
		return userRepository.save(existingUser);
	}
	
	/**
     * Actualiza el rol de un usuario en la base de datos.
     * 
     * @param email El correo electrónico del usuario.
     * @param newRole El nuevo rol que se asignará al usuario.
     */
    public void updateUserRole(String email, String newRole) {
        // Verifica si el usuario existe
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Actualiza el rol del usuario
            user.setRol(newRole);
            userRepository.save(user); // Guarda el usuario con el nuevo rol
        } else {
            throw new RuntimeException("Usuario no encontrado");
        }
    }

}
