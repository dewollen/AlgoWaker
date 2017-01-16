package vue.gui;

import metier.Variable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 10/01/2017
 */
public class PanelTrace extends JScrollPane {
    private ModeleDonnees traceVariables;

    public PanelTrace() {
        this.traceVariables = new ModeleDonnees();
        JTable table = new JTable(this.traceVariables);

        // Ajoute la table de variables dans le panel d'affichage
        this.setViewportView(table);
    }

    public void majIHM(ArrayList<Variable> variables) {
        this.traceVariables.setAlVariablesATracer(variables);
        this.traceVariables.fireTableDataChanged();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT * 4 / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
    }

    private class ModeleDonnees extends AbstractTableModel {
        private final String[] ENTETE = {"NOM", "TYPE", "VALEUR"};
        private ArrayList<Variable> alVariablesATracer;

        public ModeleDonnees() {
            this.alVariablesATracer = new ArrayList<>();
        }

        public int getColumnCount() {
            return this.ENTETE.length;
        }

        public String getColumnName(int col) {
            return this.ENTETE[col];
        }

        public int getRowCount() {
            return this.alVariablesATracer.size();
        }

        public Object getValueAt(int lig, int col) {
            Variable varTemp = this.alVariablesATracer.get(lig);

            switch (col) {
                case 0:
                    return varTemp.getNom();
                case 1:
                    return varTemp.getType();
                default:
                    return varTemp.getValeur();
            }
        }

        public void setAlVariablesATracer(ArrayList<Variable> alVariablesATracer) {
            this.alVariablesATracer = alVariablesATracer;
        }
    }
}
