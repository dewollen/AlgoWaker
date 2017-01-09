package vue.gui;

import util.donnee.Donnee;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 05/01/2017
 */
public class PanelTrace extends JScrollPane {
    private JTable traceVariables;
    private ArrayList<Donnee> alDonnees;

    PanelTrace() {
        this.alDonnees = new ArrayList<>();

        this.traceVariables = new JTable(new ModeleDonnees());

        // Ajoute la table de variables dans le panel d'affichage
        this.setViewportView(this.traceVariables);
    }

    public void setTraceVariables(ArrayList<Donnee> alDonnees) {
        this.alDonnees = alDonnees;
    }

    void majIHM() {
        this.traceVariables.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT * 4 / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
    }

    /**
     * Classe interne permettant de gérer le format du tableau de suivi des variables.
     * Permet également de ne pas pouvoir modifier les valeurs durant l'exécution.
     */
    private class ModeleDonnees extends AbstractTableModel {
        private final String[] ENTETE = {"NOM", "TYPE", "VALEUR"};

        public int getColumnCount() {
            return this.ENTETE.length;
        }

        public String getColumnName(int col) {
            return this.ENTETE[col];
        }

        public int getRowCount() {
            return alDonnees.size();
        }

        public Object getValueAt(int lig, int col) {
            Donnee tmp = alDonnees.get(lig);

            switch (col) {
                case 0:
                    return tmp.getNom();
                case 1:
                    return tmp.getType();
                default:
                    return tmp.getValeur();
            }
        }
    }
}
