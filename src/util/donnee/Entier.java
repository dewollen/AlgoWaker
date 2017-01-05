package util.donnee;

/**
 * Created by Jessy on 05/01/2017.
 */
public class Entier extends Donnee {
    private int valeur;

    public Entier(String nom, int valeur) {
        this.nom    = nom;
        this.type   = "entier";
        this.valeur = valeur;
    }
}
