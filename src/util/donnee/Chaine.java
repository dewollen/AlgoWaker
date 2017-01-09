package util.donnee;

/**
 * Classe qui simule une chaîne venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Chaine extends Donnee {

    /**
     * Constructeur de la classe Chaine
     * @param nom Nom de Chaine
     * @param suivi Déclare si oui ou non le suivi dans la trace des variables
     * @param constante Déclare si Chaine est une constante
     */
    public Chaine(String nom, boolean suivi, boolean constante) {
        this.nom       = nom;
        this.type      = "Chaîne";
        this.valeur    = "";
        this.suivi     = suivi;
        this.constante = constante;
    }

    /**
     * Mets à jour la valeur de Chaine
     * @param valeur La valeur que l'on affecter à Chaine
     */
    public void setValeur(String valeur) { this.valeur = valeur; }
}
