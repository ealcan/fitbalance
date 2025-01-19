package com.fitbalance.main.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa un usuario en el sistema. Contiene los atributos del usuario y las
 * relaciones con las recetas asociadas.
 * 
 * @author Eric
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuaris")
@Data
public class User {

	// Atributos de la entidad User

	/**
	 * Identificador único del usuario. Es generado automáticamente en la base de
	 * datos.
	 * 
	 * @return El identificador del usuario.
	 * @author Eric
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nombre de usuario utilizado para la autenticación.
	 * 
	 * @return El nombre de usuario.
	 * @author Eric
	 */
	private String username;

	/**
	 * Contraseña cifrada del usuario.
	 * 
	 * @return La contraseña cifrada.
	 * @author Eric
	 */
	private String password_hash;

	/**
	 * Dirección de correo electrónico del usuario.
	 * 
	 * @return El correo electrónico del usuario.
	 * @author Eric
	 */
	private String email;

	/**
	 * Rol del usuario (por ejemplo, "admin", "user").
	 * 
	 * @return El rol del usuario.
	 * @author Eric
	 */
	private String rol;

	// Relación con el objeto Recipes

	/**
	 * Lista de recetas asociadas al usuario. Representa el menú del usuario a
	 * través de una relación ManyToMany.
	 * 
	 * @return La lista de recetas asociadas al usuario.
	 * @author Eric
	 */
	@ManyToMany
	@JoinTable(name = "menu_usuari", joinColumns = @JoinColumn(name = "usuari_id"), inverseJoinColumns = @JoinColumn(name = "recepta_id"))
	private List<Recipes> menu;
}
