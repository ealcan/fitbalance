package com.fitbalance.main.services;

import com.fitbalance.main.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fitbalance.main.entities.SecurityUser;
import com.fitbalance.main.repository.UserRepository;

import lombok.AllArgsConstructor;

/**
 * Implementación personalizada del servicio UserDetailsService de Spring
 * Security. Se encarga de cargar el usuario desde la base de datos utilizando
 * el email para la autenticación.
 * 
 * @author Eric
 */
@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepository userRepository;

	/**
	 * Carga un usuario a partir de su email.
	 * 
	 * @param email El email del usuario a buscar.
	 * @return Un objeto UserDetails que contiene la información del usuario.
	 * @throws UsernameNotFoundException Si no se encuentra un usuario con el email
	 *                                   proporcionado.
	 * @author Eric
	 */
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(email); // Buscar por email
		if (user == null) {
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return new SecurityUser(user); // Retorna un objeto SecurityUser con los detalles del usuario
	}

}
