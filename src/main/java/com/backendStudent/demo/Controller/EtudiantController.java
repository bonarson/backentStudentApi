package com.backendStudent.demo.Controller;

import com.backendStudent.demo.Model.Etudiant;
import com.backendStudent.demo.Service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/etudiants")
public class EtudiantController {
    @Autowired
    private EtudiantService etudiantService;


    @GetMapping
    public List<Etudiant> getAllEtudiants()
    {
        return etudiantService.getAllEtudiants();
    }

    @GetMapping("/{id}")
    public Optional<Etudiant> getEtudiantsById(@PathVariable long id)
    {
        return etudiantService.getEtudiantById(id);
    }

    @GetMapping("/stats")
    public DoubleSummaryStatistics getStats()
    {
        return etudiantService.getStats();
    }

    @GetMapping("/classe/{classe}")
    public List<Etudiant> getEtudiantsByClasse(@PathVariable String classe)
    {
        return etudiantService.getEtudiantsByClasse(classe);
    }


    @PostMapping
    public Etudiant addEtudiant(@RequestBody Etudiant etudiant)
    {
        return etudiantService.addEtudiant(etudiant);
    }

    @PutMapping("/{id}/moyenne")
    public ResponseEntity<Etudiant> updateMoyenne(@PathVariable Long id, @RequestBody Map<String, Double> requestBody)
    {
        try {
            double nouvelleMoyenne = requestBody.get("moyenne");
            Etudiant updatedEtudiant = etudiantService.updateMoyenne(id, nouvelleMoyenne);
            return ResponseEntity.ok(updatedEtudiant);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}/identite")
    public Etudiant updateNomPrenom(
            @PathVariable Long id,
            @RequestParam String nom,
            @RequestParam String prenom
    ) {
        return etudiantService.updateNomPrenom(id, nom, prenom);
    }


    @DeleteMapping("/{id}")
    public void deleteEtudiant(@PathVariable Long id)
    {
        etudiantService.deleteEtudiant(id);
    }
}
