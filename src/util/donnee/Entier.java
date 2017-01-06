package util.donnee;

/**
 * Classe qui simule un entier venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Entier extends Donnee {
    private int valeur;

    public Entier(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "entier";
        this.valeur = 0;
        this.suivi  = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur) {this.valeur = Integer.parseInt(valeur);}

    public int     getValeur() {return this.valeur;}
    public String  getNom()    {return this.nom;   }
    public boolean getSuivi()  {return this.suivi; }
    public String  getType()   {return this.type;  }
}
