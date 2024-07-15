package fr.diginamic.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.diginamic.entities.Departement;

@Entity
public class Ville {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Size(min = 2, max = 100)
	@NotNull
	private String nom;

	private String codeRegion;
	private String nomRegion;
	private String codeDepartement;
	private String codeVille;
	private int populationTotale;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "departement_id")
	// @JsonBackReference
	private Departement departement;

	public Ville() {
	}

	public Ville(String codeRegion, String nomRegion, String codeDepartement, String codeVille, String nom,
			int populationTotale) {
		this.codeRegion = codeRegion;
		this.nomRegion = nomRegion;
		this.codeDepartement = codeDepartement;
		this.codeVille = codeVille;
		this.nom = nom;
		this.populationTotale = populationTotale;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getCodeRegion() {
		return codeRegion;
	}

	public void setCodeRegion(String codeRegion) {
		this.codeRegion = codeRegion;
	}

	public String getNomRegion() {
		return nomRegion;
	}

	public void setNomRegion(String nomRegion) {
		this.nomRegion = nomRegion;
	}

	public String getCodeDepartement() {
		return codeDepartement;
	}

	public void setCodeDepartement(String codeDepartement) {
		this.codeDepartement = codeDepartement;
	}

	public String getCodeVille() {
		return codeVille;
	}

	public void setCodeVille(String codeVille) {
		this.codeVille = codeVille;
	}

	public int getPopulationTotale() {
		return populationTotale;
	}

	public void setPopulationTotale(int populationTotale) {
		this.populationTotale = populationTotale;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(Departement departement) {
		this.departement = departement;
	}

    public void setDepartement(String code, String nom) {
        this.codeDepartement = departement.getCode();
        
        this.nomRegion = departement.getNom();
    }


}
