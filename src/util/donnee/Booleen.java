package util.donnee;

/**
 * Classe qui simule un booléen venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Booleen extends Donnee {
    private boolean valeur;

    public Booleen(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "booléen";
        this.valeur = false;
        this.suivi  = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur)  {this.valeur = Boolean.getBoolean(valeur); }

    public boolean getValeur() {return this.valeur;}
    public String  getNom()    {return this.nom;   }
    public String  getType()   {return this.type;  }
    public boolean getSuivi()  {return this.suivi; }
}
