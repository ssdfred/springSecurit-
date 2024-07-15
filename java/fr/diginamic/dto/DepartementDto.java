package fr.diginamic.dto;

import java.util.ArrayList;
import java.util.List;

public class DepartementDto {
    private String code;
    private String nom;
    private List<VilleDto> villes = new ArrayList<>();

    // Getters and Setters

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<VilleDto> getVilles() {
        return villes;
    }

    public void setVilles(List<VilleDto> villes) {
        this.villes = villes;
    }

 
    public void addVille(VilleDto villeDto) {
        this.villes.add(villeDto);
    }
}
