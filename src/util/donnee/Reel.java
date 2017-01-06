package util.donnee;

/**
 * Classe qui simule un réel venant du pseudo-code
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class Reel extends Donnee {
    private double valeur;

    public Reel(String nom, boolean suivi, boolean constante) {
        this.nom    = nom;
        this.type   = "réel";
        this.valeur = .0;
        this.suivi  = suivi;
        this.constante = constante;
    }

    public void setValeur(String valeur) {this.valeur = Double.parseDouble(valeur);}

    public double  getValeur() {return this.valeur;}
    public String  getType()   {return this.type;  }
    public String  getNom()    {return this.nom;   }
    public boolean getSuivi()  {return this.suivi; }

}
