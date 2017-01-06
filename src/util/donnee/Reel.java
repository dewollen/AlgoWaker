package util.donnee;

/**
 * Classe qui simule un réel venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Reel extends Donnee {
    public Reel(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "réel";
        this.valeur = ".0";
        this.suivi  = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur) { this.valeur = String.valueOf(Double.parseDouble(valeur)); }

}
