package metier.traducteur;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Jessy on 15/01/2017.
 */
public enum Primitive {
    HASARD    ("int"   , "random", false),
    PLAFOND   ("int"   , "ceil"  , true ),
    PLANCHER  ("int"   , "floor" , true ),
    ARRONDI   ("int"   , "round" , true ),
    AUJOURDHUI(""      , ""      , true ),
    JOUR      (""      , "0"      , true ),
    MOIS      (""      , "1"      , true ),
    ANNEE     (""      , "2"      , true );

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

        if(this.bParenthese)
            return String.format("(%s)Math.%s(%s)", this.type, this.equivJava, variable);

        return String.format("(%s)Math.%s() * %s", this.type, this.equivJava, variable);
    }

    public String date(String expression) {
        String variable = expression.substring(expression.indexOf("(") + 1, expression.indexOf(")")).replaceAll(" ", "");
        String[] tabDate = variable.split("/");

        return tabDate[Integer.parseInt(this.equivJava)];
    }
}
