package util.donnee;

/**
 * Classe qui simule un booléen venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Booleen extends Donnee {

    /**
     * Constructeur de la classe Booleen
     * @param nom Nom du Booleen
     * @param suivi Déclare oui ou non son suivi dans la trace des variables
     * @param constante Déclare si Booleen est une constante
     */
    public Booleen(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "booléen";
        this.valeur = "false";
        this.suivi  = suivi;
        this.constante = constante;
    }

    /**
     * Mets à jour la valeur du Booleen
     * @param valeur La valeur qu'on veut affecter au Booleen
     */
    public void setValeur(String valeur)  { this.valeur = String.valueOf(Boolean.getBoolean(valeur)); }
}
