package util.donnee;

/**
 * Classe qui gère le stockage des données
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public abstract class Donnee {
    protected String  nom;
    protected String  type;
    protected String  valeur;
    protected boolean suivi;
    protected boolean constante;

    public void setValeur(String sVal) {}
    public String getValeur()  { return new String(this.valeur); }
    public String  getNom()    { return this.nom;                }
    public boolean getSuivi()  { return this.suivi;              }
    public String  getType()   { return this.type;               }
}
