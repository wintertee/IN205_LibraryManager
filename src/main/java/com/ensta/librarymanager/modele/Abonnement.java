package com.ensta.librarymanager.modele;

public enum Abonnement {
    BASIC, PREMIUM, VIP;

    public static int getMaxEmprunt(Abonnement abonnement) {
        switch (abonnement) {
            case BASIC:
                return 2;
            case PREMIUM:
                return 5;
            case VIP:
                return 20;
            default:
                return -1;
        }
    }
}
