package vue.gui;

import controleur.Controleur;
import util.donnee.Donnee;
import vue.IVue;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.AbstractTableModel;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Classe qui gère l'affichage en mode graphique (Graphical User Interface)
 *
 * @author Win'Rs
 * @version 2017-01-05
 */
public class GUI extends JFrame implements IVue {
    private PanelAlgo pAlgo;
    private PanelTrace pTrace;
    private PanelConsole pConsole;

    static final int WIDTH = 960;
    static final int HEIGHT = 540;

    private final String TITRE = "AlgoWaker";

    private Controleur controleur;

    public GUI(Controleur controleur) {
        this.setTitle(this.TITRE);

        this.controleur = controleur;

        this.initComposants();

        this.setFocusable(true);
        this.setMinimumSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
        this.setLocationRelativeTo(null);
        this.setIconImage(new ImageIcon("images/icone.png").getImage());
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void initComposants() {
        this.setJMenuBar(new MenuRaccourcis());

        this.pAlgo = new PanelAlgo();
        this.pTrace = new PanelTrace();
        this.pConsole = new PanelConsole();

        JSplitPane pHorizontal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, this.pAlgo, this.pTrace);
        JSplitPane pVertical = new JSplitPane(JSplitPane.VERTICAL_SPLIT, pHorizontal, this.pConsole);

        this.pAlgo.setPreferredSize(this.pAlgo.getPreferredSize());
        this.pTrace.setPreferredSize(this.pTrace.getPreferredSize());
        this.pConsole.setPreferredSize(this.pConsole.getPreferredSize());

        this.add(pVertical);

        this.setVisible(true);
    }

    @Override
    public String ouvrirFichier() {
        JFileChooser explorateur = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Fichiers .algo", "algo");
        explorateur.setFileFilter(filter);

        int valBtn = explorateur.showOpenDialog(this);
        if (valBtn == JFileChooser.APPROVE_OPTION) {
            this.setTitle(this.TITRE + " - " + explorateur.getSelectedFile().getName());
            return explorateur.getSelectedFile().getAbsolutePath();
        }

        return "testsAlgo/test.algo";
    }

    @Override
    public void afficherMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    @Override
    public void afficher(String[] tabLigneCode, Integer coucou, ArrayList<Donnee> listeEtats, ArrayList<String> alConsole) {
        this.afficher(tabLigneCode, coucou, listeEtats, alConsole, "");
    }

    @Override
    public void afficher(String[] tabLigneCode, Integer coucou, ArrayList<Donnee> listeEtats, ArrayList<String> alConsole, String couleur) {
        this.pAlgo.majIHM(tabLigneCode);
        this.pTrace.majIHM(listeEtats);
        this.pConsole.majIHM(alConsole);
    }

    @Override
    public String lire() {
        return null;
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
    }

    /**
     * Barre de menus
     */
    private class MenuRaccourcis extends JMenuBar {
        MenuRaccourcis() {
            this.initComposants();
        }

        private void initComposants() {
            // Menu déroulant qui gère les fichiers
            JMenu fichier = new JMenu("Fichier");

            JMenuItem ouvrir = new JMenuItem("Ouvrir");
            JMenuItem quitter = new JMenuItem("Quitter");

            fichier.add(ouvrir);
            fichier.addSeparator();
            fichier.add(quitter);

            ouvrir.addActionListener(new OuvrirListener());
            quitter.addActionListener(new QuitterListener());

            ouvrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
            quitter.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));

            this.add(fichier);

            // Menu déroulant qui regroupe les actions
            JMenu actions = new JMenu("Actions");

            JMenuItem avancer = new JMenuItem("Avancer");

            actions.add(avancer);

            actions.addActionListener(new AvancerListener());

            actions.setMnemonic(KeyEvent.VK_ENTER);

            // Menu déroulant qui gère les fichiers
            JMenu aPropos = new JMenu("À Propos");

            JMenuItem infos = new JMenuItem("Infos");

            aPropos.add(infos);

            infos.addActionListener(new InfosListener());

            infos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_MASK));

            this.add(aPropos);
        }

        private class OuvrirListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                ouvrirFichier();
            }
        }

        private class QuitterListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.quitter();
            }
        }

        private class InfosListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.afficherInfos();
            }
        }

        private class AvancerListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                controleur.avancer();
            }
        }
    }

    public class PanelAlgo extends JScrollPane {
        private DocumentStyle doc;
        private JTextPane panelAffichage;

        private Style style;

        private final AttributeSet colorationMotCle;

        PanelAlgo() {

            this.doc = new DocumentStyle();

            this.style = doc.addStyle("Courante", null);
            StyleConstants.setBackground(this.style, new Color(102, 102, 102, 180));

            StyleContext contexte = StyleContext.getDefaultStyleContext();
            colorationMotCle = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.BLUE);

            this.panelAffichage = new JTextPane(this.doc);
            this.panelAffichage.setEditable(false);
            this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

            this.setViewportView(this.panelAffichage);
        }

        private void avancerLigneCourante() {
            int numLigne = 0;

            if (numLigne > (getViewport().getSize().getHeight() - 2) / 15)
                this.getVerticalScrollBar().setValue((int) (numLigne - getViewport().getSize().getHeight() / 15) * 15 + 7);
        }

        private void formaterCode(String[] tabLigneCode, Integer nLigne) {
            try {
                this.panelAffichage.setText(null);

                String sTemp;

                for (int i = 0; i < tabLigneCode.length; i++) {
                    sTemp = tabLigneCode[i];
                    sTemp = sTemp.replaceAll("\t", "   ");
                    sTemp = sTemp.replaceAll("◄—", "<-");

                    this.doc.colorerCode(this.doc.getLength(), nLigne, String.format("%2d %-80s\n", i, sTemp));
                }
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(GUI.WIDTH * 4 / 5, GUI.HEIGHT * 4 / 5);
        }

        @Override
        public Dimension getMinimumSize() {
            return new Dimension(GUI.WIDTH * 4 / 5, GUI.HEIGHT * 4 / 5);
        }

        public void majIHM(String[] tabLigneCode) {
        }

        private class DocumentStyle extends DefaultStyledDocument {
            private String regexMotsCles;

            DocumentStyle() {
                super();

                this.creerListeMotsCles();
            }

            void creerListeMotsCles() {
                String sTemp = "(";
                for (int i = 0; i < IVue.motsCles.length; i++)
                    sTemp += IVue.motsCles[i] + "|";

                regexMotsCles = sTemp + ")";
            }

            void colorerCode(int numeroLigne, int nLigne, String ligne) throws BadLocationException {
                int posInit = doc.getLength();

                super.insertString(posInit, ligne, (numeroLigne == nLigne) ? style : null);

                String[] mots = ligne.split("\\W+");

                for (String mot : mots) {
                    if (mot.matches(regexMotsCles))
                        setCharacterAttributes(posInit + ligne.indexOf(mot), mot.length(), colorationMotCle, false);
                }
            }
        }
    }

    public class PanelConsole extends JPanel {
        private DocumentStyle doc;
        private JTextPane panelAffichage;
        private JTextField barreEntreeValeur;

        private ArrayList<String> alConsole;

        private final StyleContext contexte = StyleContext.getDefaultStyleContext();
        private AttributeSet couleurEcrire;

        PanelConsole() {
            this.alConsole = new ArrayList<>();

            this.doc = new DocumentStyle();

            this.panelAffichage = new JTextPane(this.doc);
            this.panelAffichage.setEditable(false);
            this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
            this.panelAffichage.setFocusable(false);

            this.couleurEcrire = null;

            this.barreEntreeValeur = new JTextField();
            this.barreEntreeValeur.setEnabled(false);

            this.add(this.panelAffichage);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
        }

        @Override
        public Dimension getMinimumSize() {
            return this.getPreferredSize();
        }

        public void majIHM(ArrayList<String> al) {

        }

        void ecrireLigne(String ligne) {
            try {
                this.doc.insertString(this.doc.getLength(), ligne, this.couleurEcrire);
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
        }

        private class DocumentStyle extends DefaultStyledDocument {
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                super.insertString(offset, str, a);
                int posInit = doc.getLength();

                setCharacterAttributes(posInit, str.length(), a, false);
            }
        }
    }

    private class PanelTrace extends JScrollPane {
        private JTable traceVariables;
        private ArrayList<Donnee> alDonnees;

        PanelTrace() {
            this.alDonnees = new ArrayList<>();

            this.traceVariables = new JTable(new ModeleDonnees());

            // Ajoute la table de variables dans le panel d'affichage
            this.setViewportView(this.traceVariables);
        }

        void majIHM(ArrayList<Donnee> listeEtats) {
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
}
