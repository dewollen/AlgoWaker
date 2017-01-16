package vue.gui;

import vue.IVue;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.lang.reflect.Field;

public class PanelAlgo extends JScrollPane {
    private DocumentStyle doc;
    private JTextPane panelAffichage;

    private StyleContext contexte = StyleContext.getDefaultStyleContext();

    private AttributeSet colorationMotCle = contexte.addAttribute(contexte.getEmptySet(), StyleConstants.Foreground, Color.BLUE);

    private final Color couleurLigneCourante = new Color(102, 102, 102, 180);

    private Style style;

    public PanelAlgo() {
        this.doc = new DocumentStyle();

        this.style = doc.addStyle("Courante", null);
        StyleConstants.setBackground(this.style, this.couleurLigneCourante);

        this.panelAffichage = new JTextPane(this.doc);
        this.panelAffichage.setEditable(false);
        this.panelAffichage.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

        this.setViewportView(this.panelAffichage);
    }

    private void avancerLigneCourante(int numLigne) {
        if (numLigne > (getViewport().getSize().getHeight() - 2) / 15)
            this.getVerticalScrollBar().setValue(Math.abs((int) (numLigne - getViewport().getSize().getHeight() / 15) * 15 + 7));
    }

    public void formaterCode(String[] tabLigneCode, Integer numLigneCourante, String couleur) {
        try {
            this.panelAffichage.setText(null);

            String sTemp;

            for (int i = 0; i < tabLigneCode.length; i++) {
                sTemp = tabLigneCode[i];
                sTemp = sTemp.replaceAll("\t", "   ");
                sTemp = sTemp.replaceAll("◄—", "<-");

                this.doc.colorerCode(i, numLigneCourante, String.format("%2d %-80s\n", i, sTemp), couleur);
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        this.avancerLigneCourante(numLigneCourante);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH * 4 / 5, GUI.HEIGHT * 4 / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(GUI.WIDTH * 4 / 5, GUI.HEIGHT * 4 / 5);
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

        void colorerCode(int numeroLigne, int numLigneCourante, String ligne, String couleur) throws BadLocationException {
            int posInit = doc.getLength();

            if (couleur != null)
                StyleConstants.setBackground(style, this.getEquivalentCouleur(couleur));
            else
                StyleConstants.setBackground(style, couleurLigneCourante);

            super.insertString(posInit, ligne, (numeroLigne == numLigneCourante) ? style : null);

            String[] mots = ligne.split("\\W+");

            for (String mot : mots) {
                if (mot.matches(regexMotsCles))
                    setCharacterAttributes(posInit + ligne.indexOf(mot), mot.length(), colorationMotCle, false);
            }
        }

        private Color getEquivalentCouleur(String nomCouleur) {
            Color couleur;

            Field field;
            try {
                field = Class.forName("java.awt.Color").getField(nomCouleur.toLowerCase());
                couleur = (Color) field.get(null);
            } catch (Exception e) {
                couleur = Color.MAGENTA;
            }

            return couleur;
        }
    }
}