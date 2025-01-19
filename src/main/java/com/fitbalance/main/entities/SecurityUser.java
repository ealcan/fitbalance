package com.fitbalance.main.entities;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Clase que implementa la interfaz {@link UserDetails} de Spring Security.
 * Aquesta classe envolta un objecte {@link User} i implementa els mètodes
 * necessaris per gestionar l'autenticació i l'autorització d'un usuari dins del
 * sistema.
 */
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser implements UserDetails {

	/**
	 * Versió serialitzada per a l'objecte.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * L'usuari associat a aquesta classe. Conté la informació del perfil d'usuari.
	 */
	private User user;

	/**
	 * Retorna les autoritats (rols) associades a l'usuari. Es crea una llista amb
	 * l'autoritat del rol de l'usuari per a l'autenticació.
	 *
	 * @return Col·lecció d'autoritats de l'usuari.
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		System.out.println("Usuario encontrado: " + user.getUsername());
		System.out.println("Rol del usuario: " + user.getRol());
		System.out.println("Contraseña encriptada: " + user.getPassword_hash());
		return Collections.singletonList(new SimpleGrantedAuthority(user.getRol()));
	}

	/**
	 * Retorna la contrasenya de l'usuari.
	 *
	 * @return La contrasenya encriptada de l'usuari.
	 */
	@Override
	public String getPassword() {
		return user.getPassword_hash();
	}

	/**
	 * Retorna el nom d'usuari.
	 *
	 * @return El nom d'usuari.
	 */
	@Override
	public String getUsername() {
		return user.getUsername();
	}

	/**
	 * No és necessari implementar aquest mètode en aquest cas, ja que no es fa
	 * servir per a l'autenticació en aquest projecte.
	 * 
	 * @return El valor sempre serà {@code true}, ja que l'usuari no està bloquejat.
	 */
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * No és necessari implementar aquest mètode en aquest cas, ja que no es fa
	 * servir per a l'autenticació en aquest projecte.
	 * 
	 * @return El valor sempre serà {@code true}, ja que l'usuari no està
	 *         deshabilitat.
	 */
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * No és necessari implementar aquest mètode en aquest cas, ja que no es fa
	 * servir per a l'autenticació en aquest projecte.
	 * 
	 * @return El valor sempre serà {@code true}, ja que l'usuari no té una
	 *         contrasenya caducada.
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * Indica si el compte de l'usuari està habilitat o no. En aquest cas, sempre
	 * retornarà {@code true}, ja que l'usuari no es bloqueja pel sistema.
	 * 
	 * @return {@code true} si el compte està actiu, {@code false} si està
	 *         deshabilitat.
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}
}
