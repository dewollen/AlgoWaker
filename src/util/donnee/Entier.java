package util.donnee;

/**
 * Classe qui simule un entier venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Entier extends Donnee {
    public Entier(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "entier";
        this.valeur = "0";
        this.suivi  = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur) { this.valeur = String.valueOf(Integer.parseInt(valeur)); }
}
