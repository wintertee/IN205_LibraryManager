package com.ensta.librarymanager.modele;

public class Membre {

    private int id;
    private String nom;
    private String prenom;
    private String adresse;
    private String email;
    private String telephone;
    private Abonnement abonnement;

    public Membre(int id, String nom, String prenom, String adresse, String email, String telephone,
            Abonnement abonnement) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.email = email;
        this.telephone = telephone;
        this.abonnement = abonnement;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return this.prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Abonnement getAbonnement() {
        return this.abonnement;
    }

    public void setAbonnement(Abonnement abonnement) {
        this.abonnement = abonnement;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nom='" + getNom() + "'" +
                ", prenom='" + getPrenom() + "'" +
                ", adresse='" + getAdresse() + "'" +
                ", email='" + getEmail() + "'" +
                ", telephone='" + getTelephone() + "'" +
                ", abonnement='" + getAbonnement() + "'" +
                "}";
    }

}
