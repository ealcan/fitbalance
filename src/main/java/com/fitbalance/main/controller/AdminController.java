package com.fitbalance.main.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fitbalance.main.entities.User;
import com.fitbalance.main.services.UserService;

/**
 * Controlador de administración de usuarios. Proporciona endpoints para
 * gestionar usuarios, incluyendo obtener la lista de usuarios y eliminarlos.
 * 
 * @author Eric
 */
@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	UserService userService;

	/**
	 * Endpoint para obtener la lista de todos los usuarios.
	 * 
	 * @return Una lista de usuarios existentes en el sistema.
	 */
	@GetMapping("/users")
	public List<User> getAll() {
		return userService.getAllUsers();
	}

	/**
	 * Endpoint para eliminar un usuario específico por correo electrónico.
	 * 
	 * @param email El correo electrónico del usuario a eliminar.
	 */
	@DeleteMapping("/delete/{email}")
	public void deleteUser(@PathVariable String email) {
		userService.deleteUser(email);
	}

	/**
	 * Endpoint para eliminar todos los usuarios.
	 * 
	 * Este endpoint elimina todos los usuarios del sistema.
	 */
	@DeleteMapping("/delete/users")
	public void deleteAllUsers() {
		userService.deleteAllUsers();
	}
	
	
	/**
	 * Endpoint para actualizar el rol de un usuario a "ROLE_admin".
	 * 
	 * Este endpoint permite elevar el rol de un usuario al rol de administrador.
	 * 
	 * @param email El correo electrónico del usuario cuyo rol será actualizado.
	 */
	@PutMapping("/upgrade-role/{email}")
	public void upgradeupdateUserRole(@PathVariable String email) {
	    userService.updateUserRole(email, "ROLE_admin");
	}
	
	/**
	 * Endpoint para degradar el rol de un usuario a "ROLE_usuari".
	 * 
	 * Este endpoint permite cambiar el rol de un usuario al rol estándar o predeterminado.
	 * 
	 * @param email El correo electrónico del usuario cuyo rol será degradado.
	 */
	@PutMapping("/downgrade-role/{email}")
	public void downgradeUserRole(@PathVariable String email) {
	    userService.updateUserRole(email, "ROLE_usuari");
	}
}
