package metier.utile;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 15/01/2017
 */
public abstract class Regex {
    public static final String LIRE          = "^lire[(].+[)]\\\\*.*";
    public static final String ECRIRE        = "^ecrire[(].+[)]\\\\*.*";
    public static final String SI            = "si.*alors";
    public static final String SINON         = "sinon";
    public static final String FSI           = "fsi";
    public static final String TANTQUE       = "tantque.*faire";
    public static final String FTQ           = "ftq";
    public static final String VARIABLE      = "^[a-z].*:[A-Za-z]+";
    public static final String CONSTANTE     = "[A-Z]+<-.+";
    public static final String TABLEAUINIT   = "^[a-z].*:tableau\\[[0-9]+].+";
    public static final String TABLEAUVAR    = "^[a-z].*\\[[0-9].*].+";
    public static final String TABLEAUNUM    = "^[a-z].*\\[[a-z].*].+";

    public static boolean correspond(String expression, String regex) {
        return expression.replaceAll(" ", "").replaceAll("\t", "").toLowerCase().matches(regex);
    }
}
