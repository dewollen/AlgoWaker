package metier.traducteur;

import exception.CodeFormatException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by user on 16/01/2017.
 */
public enum PrimitiveDate {

    AUJOURDHUI(-1),
    JOUR      (0),
    MOIS      (1),
    ANNEE     (2);

    private int indice;

    PrimitiveDate(int indice) {
        this.indice = indice;
    }

    public String date(String expression) throws CodeFormatException {
        if(this.indice == -1) {
            if(!expression.equals(""))
                throw new CodeFormatException("aujourd'hui()");

            Date date = new Date();

            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return formatter.format(date);
        }

        String variable = expression.substring(expression.indexOf("(") + 1, expression.indexOf(")")).replaceAll(" ", "");
        String[] tabDate = variable.split("/");

        return tabDate[this.indice];
    }
}
//lol