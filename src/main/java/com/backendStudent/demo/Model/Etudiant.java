package com.backendStudent.demo.Model;


import jakarta.persistence.*;


@Entity
public class Etudiant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String classe;
    @Column(nullable = false)
    private double moyenne;

    @Enumerated(EnumType.STRING)
    private StatutEtudiant statut;

    public Etudiant() {
    }


    public Etudiant(Long id, String nom, String prenom, String classe, double moyenne, StatutEtudiant statut) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.classe = classe;
        this.moyenne = moyenne;
        this.statut = statut;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public double getMoyenne() {
        return moyenne;
    }

    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }

    public StatutEtudiant getStatut() {
        return statut;
    }

    public void setStatut(StatutEtudiant statut) {
        this.statut = statut;
    }

    @PrePersist
    @PreUpdate
    public void definirStatut() {
        if (moyenne >= 10) {
            this.statut = StatutEtudiant.ADMIS;
        } else if (moyenne >= 5) {
            this.statut = StatutEtudiant.REDOUBLANT;
        } else {
            this.statut = StatutEtudiant.EXCLU;
        }
    }


}
