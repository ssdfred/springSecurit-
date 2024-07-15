package fr.diginamic.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * Représente une ville.
 */
@Entity

public class Ville  {
	/**
	 * Identifiant unique de la ville.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/**
	 * Nom de la ville.
	 */
	@Size(min = 2, max = 100)
	@NotNull
	private String nom;

	/** codeRegion : code de la région */
	private String codeRegion;
	/** nomRegion : nom de la région */
	private String nomRegion;
	/** codeDepartement : code du département */
	private String codeDepartement;
	/** code INSEE de la ville */
	private String codeVille;

	/** population totale */
	private int populationTotale;



	/**
	 * Département auquel appartient la ville. Cette relation représente
	 * l'appartenance de la ville à un département. Une ville appartient à un seul
	 * département, mais un département peut contenir plusieurs villes.
	 */
    @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "departement_id")
    //@JsonBackReference
	private Departement departement;

	/**
	 * Constructeur par défaut.
	 */
	public Ville() {

	}

	/**
	 * Constructeur
	 * 
	 * @param codeRegion code de la région
	 * @param nomRegion nom de la région
	 * @param codeDepartement code du département
	 * @param codeVille code INSEE de la ville
	 * @param nom nom de la ville
	 * @param population population totale
	 */
	public Ville(String codeRegion, String nomRegion, String codeDepartement, String codeVille, String nom,
			int population) {
		super();
		this.codeRegion = codeRegion;
		this.nomRegion = nomRegion;
		this.codeDepartement = codeDepartement;
		this.codeVille = codeVille;
		this.nom = nom;
		this.populationTotale = population;
	}

	@Override
	public String toString() {
		return "Département n°" + codeDepartement + " - Ville : " + nom + " - " + populationTotale + " hab.";
	}

	/** Getter pour id
	 * @return the id 
	*/
	public int getId() {
		return id;
	}

	/** Setter pour id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/** Getter pour nom
	 * @return the nom 
	*/
	public String getNom() {
		return nom;
	}

	/** Setter pour nom
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/** Getter pour codeRegion
	 * @return the codeRegion 
	*/
	public String getCodeRegion() {
		return codeRegion;
	}

	/** Setter pour codeRegion
	 * @param codeRegion
	 */
	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}

	/** Getter pour nomRegion
	 * @return the nomRegion 
	*/
	public String getNomRegion() {
		return nomRegion;
	}

	/** Setter pour nomRegion
	 * @param nomRegion
	 */
	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	/** Getter pour codeDepartement
	 * @return the codeDepartement 
	*/
	public String getCodeDepartement() {
		return codeDepartement;
	}

	/** Setter pour codeDepartement
	 * @param codeDepartement
	 */
	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	/** Getter pour codeVille
	 * @return the codeVille 
	*/
	public String getCodeVille() {
		return codeVille;
	}

	/** Setter pour codeVille
	 * @param codeVille
	 */
	public void setCodeVille(String codeVille) {
		this.codeVille = codeVille;
	}

	/** Getter pour population
	 * @return the population 
	*/
	public int getPopulationTotale() {
		return populationTotale;
	}

	/** Setter pour population
	 * @param population
	 */
	public void setPopulationTotale(int populationTotale) {
		this.populationTotale = populationTotale;
	}



	/** Getter pour departement
	 * @return the departement 
	*/
	public Departement getDepartement() {
		return departement;
	}

	/** Setter pour departement
	 * @param departement
	 */
	public void setDepartement(Departement departement) {
		this.departement = departement;
	}



	

	

}