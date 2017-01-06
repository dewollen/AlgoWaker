package util.donnee;

/**
 * Classe qui simule un caractère venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Caractere extends Donnee {
    public Caractere(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "caractère";
        this.valeur = "";
        this.suivi  = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur) { this.valeur = valeur.substring(0, 1); }
}
