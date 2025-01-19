package com.fitbalance.main.Dto;

import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) per representar informació d'un usuari. Aquesta
 * classe encapsula dades bàsiques d'un usuari, com el nom d'usuari, el correu
 * electrònic i el rol.
 */
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuaris")
@Data
public class UserDto {

	/**
	 * Nom d'usuari (username).
	 */
	private String username;

	/**
	 * Correu electrònic de l'usuari.
	 */
	private String email;

	/**
	 * Rol de l'usuari (per exemple, administrador, usuari regular, etc.).
	 */
	private String rol;
}
