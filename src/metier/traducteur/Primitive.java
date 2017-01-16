package metier.traducteur;

/**
 * Created by Jessy on 15/01/2017.
 */
public enum Primitive {
    HASARD   ("int"   , "random", false),
    COSINUS  ("double", "cos"   , true ),
    SINUS    ("double", "sin"   , true ),
    PLAFOND  ("int"   , "ceil"  , true ),
    PLANCHER ("int"   , "floor" , true ),
    ARRONDI  ("int"   , "round" , true ),
    PUISSANCE("double", "pow"   , true );

    private String  type;
    private String  equivJava;
    private boolean bParenthese; // false ssi on soit multiplier la primitive

    Primitive(String type, String equivJava, boolean bParenthese) {
        this.type        = type;
        this.equivJava   = equivJava;
        this.bParenthese = bParenthese;
    }

    public String calculer(String expression) {
        String variable = expression.substring(expression.indexOf("(") + 1, expression.indexOf(")")).replaceAll(" ", "");

        if (this.equals(Primitive.PUISSANCE)) {
            String[] tabPuissance = variable.split("\\^");
            return String.format("(%s)Math.%s(%s, %s)", this.type, this.equivJava, tabPuissance[0], tabPuissance[1]);
        }

        if(this.bParenthese)
            return String.format("(%s)Math.%s(%s)", this.type, this.equivJava, variable);

        return String.format("(%s)Math.%s() * %s", this.type, this.equivJava, variable);
    }
}
