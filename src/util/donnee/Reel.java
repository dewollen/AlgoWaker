package util.donnee;

/**
 * Classe qui simule un réel venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Reel extends Donnee {
    private double valeur;

    public Reel(String nom, boolean suivi) {
        this.nom    = nom;
        this.type   = "réel";
        this.valeur = .0;
        this.suivi  = suivi;
    }

    public void setValeur(double valeur) {
        this.valeur = valeur;
    }
}
