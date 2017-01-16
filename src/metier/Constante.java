package metier;

import exception.ConstantChangeException;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public class Constante extends Variable {
    public Constante(String nom, String type, String valeur) {
        super(nom.toUpperCase(), type, valeur, false);
    }

    @Override
    public void setValeur(String valeur) throws ConstantChangeException {
        throw new ConstantChangeException(); // On ne peut pas changer la valeur d'une constante
    }
}
