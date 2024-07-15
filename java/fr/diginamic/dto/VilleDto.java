package fr.diginamic.dto;

public class VilleDto {
    private String codeRegion;
    private String nomRegion;
    private String codeDepartement;
    private String codeVille;
    private String nom;
    private int populationTotale;
    private DepartementDto departement;

 // Constructor
    public VilleDto(String codeRegion, String nomRegion, String codeDepartement, String codeVille, String nom, int populationTotale, DepartementDto departement) {
        this.codeRegion = codeRegion;
        this.nomRegion = nomRegion;
        this.codeDepartement = codeDepartement;
        this.codeVille = codeVille;
        this.nom = nom;
        this.populationTotale = populationTotale;
        this.departement = departement;
    }
    // Getters and Setters

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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPopulationTotale() {
        return populationTotale;
    }

    public void setPopulationTotale(int populationTotale) {
        this.populationTotale = populationTotale;
    }

    public DepartementDto getDepartement() {
        return departement;
    }

    public void setDepartement(DepartementDto departement) {
        this.departement = departement;
    }

}
