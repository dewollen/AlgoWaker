package util.donnee;

/**
 * Classe qui simule un caractère venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Caractere extends Donnee {
    private char valeur;

    public Caractere(String nom, boolean suivi) {
        this.nom    = nom;
        this.type   = "caractère";
        this.valeur = '\0';
        this.suivi  = suivi;
    }

    public void setValeur(char valeur) {
        this.valeur = valeur;
    }
}