package vue.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Classe qui sert à ...
 *
 * @author thomasdigregorio
 * @version 05/01/2017
 */
public class PanelConsole extends JScrollPane {
    public PanelConsole() {

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GUI.WIDTH / 5, GUI.HEIGHT / 5);
    }

    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }
}
