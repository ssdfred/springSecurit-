package fr.diginamic.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import fr.diginamic.dto.DepartementDto;
import fr.diginamic.dto.VilleDto;
import fr.diginamic.entities.Departement;
import fr.diginamic.entities.Ville;
import fr.diginamic.mappers.DepartementMapper;
import fr.diginamic.mappers.VilleMapper;
import fr.diginamic.repository.DepartementRepository;
import fr.diginamic.repository.VilleRepository;
import fr.diginamic.service.DepartementService;
import fr.diginamic.service.ExportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
@RestController
@RequestMapping("/departements")
public class DepartementController {

	private DepartementService departementService;
    private final DepartementRepository departementRepository;
    private final VilleRepository villeRepository;
    @Autowired
    private ExportService exportService;

    @Autowired
    public DepartementController(DepartementRepository departementRepository, VilleRepository villeRepository) {
        this.departementRepository = departementRepository;
        this.villeRepository = villeRepository;
    }

    @GetMapping
    public List<DepartementDto> getAllDepartements() {
        List<Departement> departements = departementRepository.findAll();
        return departements.stream()
                .map(DepartementMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartementDto> getDepartementById(@PathVariable int id) {
        Optional<Departement> departementOpt = departementRepository.findById(id);
        if (departementOpt.isPresent()) {
            DepartementDto departementDto = DepartementMapper.toDto(departementOpt.get());
            return new ResponseEntity<>(departementDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/ajouter")
    public ResponseEntity<String> ajouterDepartements(@RequestBody List<DepartementDto> departementDtos) {
        departementService.ajouterDepartements(departementDtos);
        return ResponseEntity.ok("Départements ajoutés avec succès");
    }

    @PutMapping("/{id}")
    public DepartementDto updateDepartement(@PathVariable int id, @RequestBody DepartementDto departementDto) {
        Departement departement = departementRepository.findById(id).orElseThrow();
        departement.setCode(departementDto.getCode());
        departement.setNom(departementDto.getNom());
        
        Departement updatedDepartement = departementRepository.save(departement);
        return DepartementMapper.toDto(updatedDepartement);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartement(@PathVariable int id) {
        Departement departement = departementRepository.findById(id).orElseThrow();
        departementRepository.delete(departement);
        return ResponseEntity.ok().build();
    }
    @Operation(summary = "Permet d'obetenir la liste des villes d'un departement")
	@ApiResponses(value = {
	  @ApiResponse(responseCode = "200",
			       description = "Retourne la liste des villes du departement selectionner",
			       content = { @Content(mediaType = "application/json",
			       schema = @Schema(implementation = VilleDto.class)) }),
	  @ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.",
	    			content = @Content)})
    @GetMapping("/{id}/villes")
    public ResponseEntity<List<VilleDto>> getVillesByPopulationRange(
            @PathVariable int id,
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {

        List<Ville> villes;

        if (min != null && max != null) {
            villes = departementRepository.findVillesByPopulationTotaleRange(id, min, max);
        } else {
            villes = departementRepository.findTopNVillesByDepartementOrderByPopulationTotaleDesc(id);
        }

        List<VilleDto> villeDtos = villes.stream().map(VilleMapper::toDto).collect(Collectors.toList());

        // Ajoutez des logs pour le débogage
//        System.out.println("Paramètres min et max : min = " + min + ", max = " + max);
//        System.out.println("Résultat de la requête SQL : " + villeDtos);

        return  ResponseEntity.ok().body(villeDtos);
    }
//    @GetMapping("/export/{codeDepartement}")
//    public ResponseEntity<String> exportDepartementToPDF(@PathVariable String codeDepartement) {
//        pdfExportService.exportDepartementToPDF(codeDepartement);
//        return new ResponseEntity<>("Export PDF en cours", HttpStatus.OK);
//    }
    @Operation(summary = "Permet d'exporter la liste des villes d'un departement")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Retourne la liste des villes du departement en PDF", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = VilleDto.class)) }),
			@ApiResponse(responseCode = "400", description = "Si une règle métier n'est pas respectée.", content = @Content) })
    @GetMapping("/export/pdf/{codeDepartement}")
    public void ficheVille(@PathVariable String codeDepartement, HttpServletResponse response) throws IOException, DocumentException {
        response.setHeader("Content-Disposition", "attachment; filename=\"departement.pdf\"");
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        document.addTitle("Fiche Département");
        document.newPage();

        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        Font titleFont = new Font(baseFont, 32.0f, Font.BOLD, new BaseColor(0, 51, 80));
        Font normalFont = new Font(baseFont, 12.0f, Font.NORMAL, BaseColor.BLACK);

        String nomDepartement = exportService.fetchDepartementName(codeDepartement);
        document.add(new Phrase("Département: " + nomDepartement, titleFont));
        document.add(new Phrase("\nCode Département: " + codeDepartement, normalFont));
        document.add(new Phrase("\n\n"));

        exportService.addVilleDetailsToPdf(document, codeDepartement, normalFont);

        document.close();
    }
}
