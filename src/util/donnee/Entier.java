package util.donnee;

/**
 * Classe qui simule un entier venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Entier extends Donnee {
    private int valeur;

    public Entier(String nom, boolean suivi) {
        this.nom    = nom;
        this.type   = "entier";
        this.valeur = 0;
        this.suivi  = suivi;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
