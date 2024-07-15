package fr.diginamic.mappers;

import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Ville;

public class VilleMapper {

    public static VilleDto toDto(Ville ville) {
        VilleDto villeDto = new VilleDto();
        villeDto.setNom(ville.getNom());
        villeDto.setPopulationTotale(ville.getPopulationTotale());
        villeDto.setCodeRegion(ville.getCodeRegion());
        villeDto.setNomRegion(ville.getNomRegion());
        villeDto.setCodeDepartement(ville.getCodeDepartement());
        villeDto.setCodeVille(ville.getCodeVille());
        villeDto.setDepartement(DepartementMapper.toDto(ville.getDepartement()));
        return villeDto;
    }

    public static Ville toEntity(VilleDto villeDto) {
        Ville ville = new Ville();
        ville.setCodeRegion(villeDto.getCodeRegion());
        ville.setNomRegion(villeDto.getNomRegion());
        ville.setCodeDepartement(villeDto.getCodeDepartement());
        ville.setCodeVille(villeDto.getCodeVille());
        ville.setNom(villeDto.getNom());
        ville.setPopulationTotale(villeDto.getPopulationTotale());
        return ville;
    }
}
