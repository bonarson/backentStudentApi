package com.backendStudent.demo.Service;


import com.backendStudent.demo.Model.Etudiant;
import com.backendStudent.demo.Repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;


@Service
public class EtudiantService {
    @Autowired
    private EtudiantRepository etudiantRepository;


    public List<Etudiant> getAllEtudiants() {
        return etudiantRepository.findAll();
    }

    public Etudiant addEtudiant(Etudiant etudiant) {
        etudiant.definirStatut();
        return etudiantRepository.save(etudiant);
    }

    public Etudiant updateMoyenne(Long id, double nouvelleMoyenne) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);
        if (optionalEtudiant.isPresent()) {
            Etudiant etudiant = optionalEtudiant.get();
            etudiant.setMoyenne(nouvelleMoyenne);
            etudiant.definirStatut();
            return etudiantRepository.save(etudiant);
        } else {

            throw new RuntimeException("Étudiant non trouvé avec l'ID : " + id);
        }
    }

    public void deleteEtudiant(Long id) {
        etudiantRepository.deleteById(id);
    }

    public Optional<Etudiant> getEtudiantById(Long id) {
        return etudiantRepository.findById(id);
    }

    public DoubleSummaryStatistics getStats() {
        return etudiantRepository.findAll().stream()
                .mapToDouble(Etudiant::getMoyenne)
                .summaryStatistics();
    }

}
