package metier;

import exception.ConstantChangeException;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public class Variable {
    protected String nom;
    protected String type;
    protected String valeur;
    protected boolean suivi;

    public Variable(String nom, String type, String valeur, boolean suivi) {
        this.nom = nom;
        this.type = type;
        this.valeur = valeur;
        this.suivi = suivi;
    }

    public Variable(Variable variable) {
        this.nom = variable.getNom();
        this.type = variable.getType();
        this.valeur = variable.getValeur();
        this.suivi = variable.isSuivi();
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getValeur() {
        return valeur;
    }

    public boolean isSuivi() {
        return suivi;
    }

    public void setValeur(String valeur) throws ConstantChangeException {
        this.valeur = valeur;
    }

    @Override
    public String toString() {
        return "Variable{" +
                "nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", valeur='" + valeur + '\'' +
                ", suivi=" + suivi +
                '}';
    }
}
