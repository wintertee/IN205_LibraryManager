package com.ensta.librarymanager.modele;

import java.time.LocalDate;

public class Emprunt {
    private int id;
    private Membre membre;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetour;

    public Emprunt(int id, Membre membre, Livre livre, LocalDate dateEmprunt, LocalDate dateRetour) {
        this.id = id;
        this.membre = membre;
        this.livre = livre;
        this.dateEmprunt = dateEmprunt;
        this.dateRetour = dateRetour;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Membre getMembre() {
        return this.membre;
    }

    public void setMembre(Membre membre) {
        this.membre = membre;
    }

    public Livre getLivre() {
        return this.livre;
    }

    public void setLivre(Livre livre) {
        this.livre = livre;
    }

    public LocalDate getDateEmprunt() {
        return this.dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) {
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetour() {
        return this.dateRetour;
    }

    public void setDateRetour(LocalDate dateRetour) {
        this.dateRetour = dateRetour;
    }

}
