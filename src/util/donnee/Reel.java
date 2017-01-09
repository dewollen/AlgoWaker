package util.donnee;

/**
 * Classe qui simule un réel venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Reel extends Donnee {

    /**
     * Constructeur de la classe Reel
     *
     * @param nom       Nom de Reel
     * @param suivi     Déclare si oui ou non son suivi dans la trace des variables
     * @param constante Délcare si Reel est une constante
     */
    public Reel(String nom, boolean suivi, boolean constante) {
        this.nom = nom;
        this.type = "réel";
        this.valeur = ".0";
        this.suivi = suivi;
        this.constante = constante;
    }

    /**
     * Mets à jour la valeur de Reel
     *
     * @param valeur La valeur que l'on veut affecter à Reel
     */
    public void setValeur(String valeur) {
        this.valeur = String.valueOf(Double.parseDouble(valeur));
    }

}
