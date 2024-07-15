package fr.diginamic.controller;

import java.lang.ProcessBuilder.Redirect;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.service.DepartementService;
import fr.diginamic.service.VilleService;

@Service
public class TownController {
	@Autowired
	private VilleService villeService;
	@Autowired
	private DepartementService departementService;

	private static final Logger logger = LoggerFactory.getLogger(TownController.class);

	@GetMapping("/townList")
	public String getVille(Model model) {
		List<Ville> villes = villeService.getAllVilles();

		// Log the size of the list to check if data is fetched
		logger.info("Number of Villes fetched: " + villes.size());

		List<VilleDto> villeDtos = villes.stream()
				.map(ville -> new VilleDto(ville.getCodeRegion(), ville.getNomRegion(), ville.getCodeDepartement(),
						ville.getCodeVille(), ville.getNom(), ville.getPopulationTotale(),
						new DepartementDto(ville.getDepartement().getNom(), ville.getDepartement().getCode())))
				.collect(Collectors.toList());

		// Log the size of the list to check if mapping is correct
		logger.info("Number of VilleDtos: " + villes.size());

		model.addAttribute("villes", villeDtos);
		return "town/townList";
		
		
	}

	@GetMapping("/deleteVille")
	public String deleteTown(@RequestParam("id") int id) {
		villeService.deleteVille(id);
		return "redirect:/townList";
	}

	@GetMapping("/departementList")
    public String getDepartement(Model model) {
        // Récupération des départements depuis le service
        List<Departement> departements = departementService.getAllDepartement();

        // Log pour vérifier la taille de la liste récupérée
        System.out.println("Number of Departements fetched: " + departements.size());

        // Transformation des départements en DTOs
        List<DepartementDto> departementDtos = (List<DepartementDto>) departements.stream().map(departement -> new DepartementDto(departement.getNom(),departement.getCode())).collect(Collectors.toList()) ;


        model.addAttribute("departements", departementDtos);
        return "town/departementList";
    }

	@GetMapping("/deletedepartement")
	public String deletedep(@RequestParam("id") int id) {
		departementService.deleteDepartement(id);
		return "redirect:/townList";
	}
}
