package metier.utile;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 15/01/2017
 */
public abstract class Regex {
    public static final String LIRE    = "^lire[(].+[)]\\\\*.*";
    public static final String ECRIRE  = "^ecrire[(].+[)]\\\\*.*";
    public static final String SI      = "si.*alors";
    public static final String SINON   = "sinon";
    public static final String FSI     = "fsi";
    public static final String TANTQUE = "tantque.*faire";
    public static final String FTQ     = "ftq";

    public static boolean correspond(String expression, String regex) {
        return expression.replaceAll(" ", "").replaceAll("\t", "").toLowerCase().matches(regex);
    }
}
