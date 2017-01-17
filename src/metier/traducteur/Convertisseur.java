package metier.traducteur;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 15/01/2017
 */
public abstract class Convertisseur {
    public static String convertirArithmetique(String expression) {
        expression = expression.replaceAll("[\t ]", "");
        expression = expression.replaceAll("/1", "");
        expression = expression.replaceAll("x", "*");

        if (expression.contains("/"))
            expression = Convertisseur.convertirDivisionReelle(expression);

        expression = expression.replaceAll("div", "/");
        expression = expression.replaceAll("mod", "%");

        return expression;
    }

    public static String convertirBooleen(String expression) {
        expression = expression.replaceAll("[\t ]", "");
        expression = expression.replaceAll("=", "==");
        expression = expression.replaceAll("et", "&&");
        expression = expression.replaceAll("ou", "||");

        expression = Convertisseur.convertirArithmetique(expression);

        return expression;
    }

    private static String convertirDivisionReelle(String expression) {
        String[] tabExpression = expression.split("/");

        // On reconstruit la chaine
        expression = "";

        for (int i = 0; i < tabExpression.length - 1; i++)
            expression += (tabExpression[i].matches("[0-9]+") ? "(double)" : "") + tabExpression[i] + "/";

        expression += tabExpression[tabExpression.length - 1];

        return "Math.round(" + expression + " * 100.0) / 100.0";
    }
}
