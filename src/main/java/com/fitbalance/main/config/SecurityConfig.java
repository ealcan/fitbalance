package com.fitbalance.main.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fitbalance.main.entities.User;
import com.fitbalance.main.services.UserDetailsServiceImpl;
import com.fitbalance.main.services.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.cors.CorsConfigurationSource;

import java.io.IOException;
import java.util.Arrays;

/**
 * Configuración de seguridad de la aplicación. Proporciona las reglas de
 * autorización y configuración de CORS, CSRF, y autenticación.
 * 
 * @author Eric
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private TokenUtils tokenUtils;

	/**
	 * Configura las reglas de seguridad de la aplicación. Define la autorización,
	 * el control de acceso a las rutas, y la configuración de login.
	 * 
	 * @param http Configuración de HTTP de seguridad.
	 * @return La configuración del filtro de seguridad.
	 * @throws Exception Si ocurre un error durante la configuración de seguridad.
	 * @author Eric
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    return http.cors(cors -> cors.configurationSource(corsConfigurationSource())) // Configuración de CORS
	            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF
	            .requiresChannel(channel -> channel.anyRequest().requiresSecure()) // Redirige HTTP a HTTPS
	            .authorizeHttpRequests(auth -> auth
	                    .requestMatchers("/auth/login", "/auth/register").permitAll()
	                    .requestMatchers("/admin/*", "/receptes/new", "/ingredients/new").hasAuthority("ROLE_admin")
	                    .anyRequest().authenticated())
	            .formLogin(form -> form.loginProcessingUrl("/auth/login")
	                    .successHandler((request, response, authentication) -> {
	                        response.setContentType("application/json");
	                        response.getWriter().write("{\"success\": true, \"message\": \"Benvingut\"}");
	                        response.setStatus(HttpServletResponse.SC_OK);
	                    })
	                    .failureHandler((request, response, exception) -> {
	                        response.setContentType("application/json");
	                        response.getWriter().write("{\"success\": false, \"message\": \"Credencials invàlides\"}");
	                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	                    }))
	            .authenticationProvider(authenticationProvider())
	            .httpBasic().disable()
	            .build();
	}


	/**
	 * Proveedor de autenticación que utiliza un servicio de detalles de usuario.
	 * Configura la autenticación con un codificador de contraseñas.
	 * 
	 * @return El proveedor de autenticación.
	 * @author Eric
	 */
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder()); // Establece el codificador de contraseñas
		return authProvider;
	}

	/**
	 * Configura las reglas de CORS para la aplicación. Permite solicitudes de
	 * cualquier origen y métodos específicos.
	 * 
	 * @return La configuración de CORS.
	 * @author Eric
	 */
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*")); // Permite todos los orígenes
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Métodos permitidos
		configuration.setAllowCredentials(true); // Permite el uso de credenciales
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Encabezados permitidos

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration); // Aplica la configuración a todas las rutas
		return source;
	}

	/**
	 * Define el codificador de contraseñas utilizado en la autenticación. En este
	 * caso, se utiliza el codificador BCrypt.
	 * 
	 * @return El codificador de contraseñas.
	 * @author Eric
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // Utiliza el codificador BCrypt
	}
}
