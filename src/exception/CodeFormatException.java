package exception;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 11/01/2017
 */
public class CodeFormatException extends Exception {
    private String erreur;

    public CodeFormatException(int numLigne, String ligne) {
        this.erreur = "Erreur dans le format du pseudo-code à la ligne " + numLigne + " :\n" + ligne;
    }

    @Override
    public String getMessage() {
        return erreur;
    }
}