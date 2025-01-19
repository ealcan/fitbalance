package com.fitbalance.main.entities;

import java.util.ArrayList;
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
 * Entitat que representa una recepta dins de la base de dades. Cada recepta té
 * informació relacionada amb el seu identificador únic, nom, tipus de menjar
 * (apat), les calories totals i una llista d'ingredients associats.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "receptes")
public class Recipes {

	/**
	 * Identificador únic de la recepta. Generat automàticament mitjançant
	 * l'estratègia d'identitat.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * Nom de la recepta (per exemple, "Amanida Mediterrània").
	 */
	private String nom;

	/**
	 * Tipus de menjar associat amb la recepta (per exemple, "Almuerzo", "Comida",
	 * "Merienda", etc.).
	 */
	private String apat;

	/**
	 * Quantitat total de calories que proporciona la recepta. Expressada com a suma
	 * de les calories dels ingredients associats.
	 */
	private Double caloriasTotals;

	/**
	 * Llista d'ingredients associats a la recepta. Relacionat mitjançant una
	 * relació ManyToMany amb l'entitat {@link Ingredients}.
	 */
	@ManyToMany
	@JoinTable(name = "receptes_ingredients", // Taula intermèdia que uneix receptes i ingredients
			joinColumns = @JoinColumn(name = "recepta_id"), // Clau forana per a la recepta
			inverseJoinColumns = @JoinColumn(name = "ingredient_id") // Clau forana per als ingredients
	)
	private List<Ingredients> ingredientes = new ArrayList<>();
}
