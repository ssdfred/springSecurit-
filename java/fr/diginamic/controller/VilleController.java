package fr.diginamic.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.mappers.VilleMapper;
import fr.diginamic.repository.DepartementRepository;
import fr.diginamic.repository.VilleRepository;
import fr.diginamic.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/villes")
public class VilleController {

	@Autowired
	private VilleRepository villeRepository;

	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private ExportService exportService;

	@GetMapping("/export/csv")
	public ResponseEntity<InputStreamResource> exportVillesToCsv(@RequestParam int minPopulation) throws IOException {
		List<VilleDto> villes = exportService.findVillesWithMinPopulation(minPopulation);
		ByteArrayInputStream byteArrayInputStream = exportService.exportVillesToCsv(villes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=villes.csv");

		return ResponseEntity.ok().headers(headers).contentType(MediaType.valueOf("application/csv"))
				.body(new InputStreamResource(byteArrayInputStream));
	}

	@Operation(summary = "Permet d'obetenir la liste des villes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retourne la liste des villes", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.", content = @Content) })
	@GetMapping
	public List<VilleDto> getAllVilles() {
		List<Ville> villes = villeRepository.findAll();
		return villes.stream().map(VilleMapper::toDto).collect(Collectors.toList());
	}

	@PostMapping
	public ResponseEntity<Object> createVille(@RequestBody VilleDto villeDto) {
		if (villeDto.getDepartement() == null || villeDto.getDepartement().getCode() == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Le code du département est obligatoire");
		}

		Departement departement = departementRepository.findByCode(villeDto.getDepartement().getCode());

		if (departement == null) {
			departement = new Departement();
			departement.setNom(villeDto.getDepartement().getNom());
			departement.setCode(villeDto.getDepartement().getCode());
			departement = departementRepository.save(departement);
		}

		Ville ville = VilleMapper.toEntity(villeDto);
		ville.setDepartement(departement);
		Ville savedVille = villeRepository.save(ville);
		VilleDto savedVilleDto = VilleMapper.toDto(savedVille);

		return new ResponseEntity<>(savedVilleDto, HttpStatus.CREATED);
	}

	@Operation(summary = "Permet d'obetenir la liste des villes en indiquant un min et un max")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retourne la liste des villes comprise entre un min et un max", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.", content = @Content) })
	@GetMapping("/{id}/villes/filter")
	public ResponseEntity<List<VilleDto>> getVillesByPopulationRange(@PathVariable int id, @RequestParam int min,
			@RequestParam int max) {
		List<Ville> villes = villeRepository.findVillesByPopulationTotaleRange(id, min, max);
		List<VilleDto> villeDtos = villes.stream().map(VilleMapper::toDto).collect(Collectors.toList());
		return  ResponseEntity.ok().body(villeDtos);
	}
	 
}
