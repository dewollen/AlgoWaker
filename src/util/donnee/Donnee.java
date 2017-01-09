package util.donnee;

/**
 * Classe qui gère le stockage des données
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public abstract class Donnee {
    /**
     * Nom de la variable/constante
     *
     * @see Donnee#getNom()
     */
    protected String nom;

    /**
     * Type de la variable/constante
     *
     * @see Donnee#getType()
     */
    protected String type;

    /**
     * Valeur de la variable/constante
     *
     * @see Donnee#getValeur()
     * @see Donnee#setValeur(String)
     */
    protected String valeur;

    /**
     * Vrai si la variable/constante est suivi par l'utilisateur, si non faux
     *
     * @see Donnee#getSuivi()
     */
    protected boolean suivi;

    /**
     * Vrai si le type est une constante, si non faux
     *
     * @see Donnee#getConstante()
     */
    protected boolean constante;

    public void setValeur(String sVal) {
    }

    public String getValeur() {
        return new String(this.valeur);
    }

    public String getNom() {
        return this.nom;
    }

    public boolean getSuivi() {
        return this.suivi;
    }

    public String getType() {
        return this.type;
    }

    public boolean getConstante() {
        return this.constante;
    }
}
