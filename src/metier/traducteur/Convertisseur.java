package metier.traducteur;

import bsh.EvalError;
import bsh.Interpreter;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 15/01/2017
 */
public abstract class Convertisseur {
    private static Interpreter interpreter = new Interpreter();

    public static String evaluerArithmetique(String expression) throws EvalError {
        expression = String.valueOf(interpreter.eval(convertirArithmetique(expression)));

        String[] tabDecimales = expression.split("\\.");

        if (tabDecimales.length == 2 && tabDecimales[1].matches("0+"))
            return tabDecimales[0];

        return expression;
    }

    public static boolean evaluerBooleen(String expression) throws EvalError {
        return Boolean.parseBoolean(String.valueOf(interpreter.eval(convertirBooleen(expression))));
    }

    public static String convertirArithmetique(String expression) {
        expression = expression.replaceAll("[\t ]", "");
        expression = expression.replaceAll("/1", "");
        expression = expression.replaceAll("×", "*");

        if (expression.contains("/"))
            expression = Convertisseur.convertirDivisionReelle(expression);

        expression = expression.replaceAll("div", "/");
        expression = expression.replaceAll("mod", "%");
        expression = expression.replaceAll("\\\\/̄", "Math.sqrt");

        return expression;
    }

    public static String convertirBooleen(String expression) {
        expression = expression.replaceAll("[\t ]", "");
        expression = expression.replaceAll("=", "==");
        expression = expression.replaceAll("et", "&&");
        expression = expression.replaceAll("ou", "||");

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
