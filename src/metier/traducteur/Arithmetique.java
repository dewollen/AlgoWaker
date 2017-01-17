package metier.traducteur;

/**
 * Created by user on 16/01/2017.
 */
public enum Arithmetique {
    COSINUS  ("double", "cos"   , true),
    SINUS    ("double", "sin"   , true),
    PUISSANCE("double", "pow"   , true),
    RACINE   ("double", "sqrt"  , true);

    private String  type;
    private String  equivJava;
    private boolean bParenthese; // false ssi on soit multiplier la fonction

    Arithmetique(String type, String equivJava, boolean bParenthese) {
        this.type        = type;
        this.equivJava   = equivJava;
        this.bParenthese = bParenthese;
    }

    public String calculer(String expression) {
        String variable = expression.substring(expression.indexOf("(") + 1, expression.indexOf(")")).replaceAll(" ", "");

        if (this.equals(metier.traducteur.Arithmetique.PUISSANCE)) {
            String[] tabPuissance = variable.split("\\^");
            return String.format("(%s)Math.%s(%s, %s)", this.type, this.equivJava, tabPuissance[0], tabPuissance[1]);
        }

        if(this.bParenthese)
            return String.format("(%s)Math.%s(%s)", this.type, this.equivJava, variable);

        return String.format("(%s)Math.%s() * %s", this.type, this.equivJava, variable);
    }
}