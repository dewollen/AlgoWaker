package util.donnee;

/**
 * Classe qui simule un booléen venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Booleen extends Donnee {
    private boolean valeur;

    public Booleen(String nom, boolean suivi) {
        this.nom    = nom;
        this.type   = "booléen";
        this.valeur = false;
        this.suivi  = suivi;
    }

    public void setValeur(boolean valeur) {
        this.valeur = valeur;
    }
}
