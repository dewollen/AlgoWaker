package util.donnee;

/**
 * Classe qui simule un entier venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Entier extends Donnee {

    /**
     * Constructeur de la classe Entier
     *
     * @param nom       Nom de Entier
     * @param suivi     Déclare si oui ou non son suivi dans la trace des variables
     * @param constante Déclare si Entier est une constante
     */
    public Entier(String nom, boolean suivi, boolean constante) {
        this.nom = nom;
        this.type = "entier";
        this.valeur = "0";
        this.suivi = suivi;
        this.constante = constante;
    }

    /**
     * Mets à jour la valeur de Entier
     *
     * @param valeur La valeur que l'on veut affecter à Entier
     */
    public void setValeur(String valeur) {
        this.valeur = String.valueOf(Integer.parseInt(valeur));
    }
}
