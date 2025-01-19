package com.fitbalance.main.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entitat que representa un ingredient dins de la base de dades. Cada
 * ingredient té informació relacionada amb el seu identificador únic, nom,
 * calories, quantitat i unitat de mesura.
 */
@Entity
@NoArgsConstructor
@Data
@Table(name = "ingredients")
public class Ingredients {

	/**
	 * Identificador únic de l'ingredient. Generat automàticament mitjançant
	 * l'estratègia d'identitat.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nom de l'ingredient (per exemple, "Tomàquet").
	 */
	private String nom;

	/**
	 * Quantitat de calories de l'ingredient. Especificada en unitats de calories
	 * (per exemple, 50.0).
	 */
	private Double calories;

	/**
	 * Quantitat de l'ingredient. Aquesta quantitat varia segons l'ingredient (per
	 * exemple, 100.0 grams o 2.0 unitats).
	 */
	private Double cantitat;

	/**
	 * Unitat de mesura de l'ingredient (per exemple, "grams", "unitats").
	 */
	private String unitat;
}
