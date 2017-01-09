package util.donnee;

/**
 * Classe qui simule un caractère venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Caractere extends Donnee {

    /**
     * Constructeur de la classe Caractere
     *
     * @param nom       Nom de Caractere
     * @param suivi     Déclare si oui ou non son suivi dans la trace des variables
     * @param constante Déclare si le Caractere est une constante
     */
    public Caractere(String nom, boolean suivi, boolean constante) {
        this.nom = nom;
        this.type = "caractère";
        this.valeur = "";
        this.suivi = suivi;
        this.constante = constante;
    }

    /**
     * Mets à jour la valeur de Caractere
     *
     * @param valeur Valeur que l'on veut affecter à Caractere
     */
    public void setValeur(String valeur) {
        this.valeur = valeur.substring(0, 1);
    }
}
