package exception;

/**
 * Created by Jessy on 16/01/2017.
 */
public class UnexpectedTypeException extends Exception {
    private String erreur;

    public UnexpectedTypeException(String typeVariable) {
        this.erreur = "Erreur : Impossible d'affecter cette valeur à une variable de type " + typeVariable;
    }

    @Override
    public String getMessage() {
        return erreur;
    }
}
