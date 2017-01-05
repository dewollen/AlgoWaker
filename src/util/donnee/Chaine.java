package util.donnee;

/**
 * Classe qui simule une chaîne venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Chaine extends Donnee {
    private String valeur;

    public Chaine(String nom, boolean suivi) {
        this.nom    = nom;
        this.type   = "Chaîne";
        this.valeur = null;
        this.suivi  = suivi;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }
}
