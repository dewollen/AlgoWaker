package util.donnee;

/**
 * Classe qui simule une chaîne venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Chaine extends Donnee {
    public Chaine(String nom, boolean suivi, boolean constante) {
        this.nom       = nom;
        this.type      = "Chaîne";
        this.valeur    = null;
        this.suivi     = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur) { this.valeur = valeur; }
}
