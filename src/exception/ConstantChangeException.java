package exception;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 11/01/2017
 */
public class ConstantChangeException extends Exception {
    private String erreur;

    public ConstantChangeException() {
        this.erreur = "Erreur : Une constante ne peut pas changer de valeur.";
    }

    @Override
    public String getMessage() {
        return erreur;
    }
}
