package ui;

import javax.swing.*;

/**
 * this class creates a windows with a title and message and renders them.
 */
public class Modal extends JDialog {

    /**
     * Constructor
     * @param parent the parent JFrame that will be using it.
     * @param title title of the window.
     * @param message message inside the window.
     */
    public Modal( JFrame parent, String title, String message ) {
        super(parent, title, true);

        JPanel panel = new JPanel();
        JLabel label = new JLabel(message);

        panel.add(label);
        getContentPane().add(panel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setVisible(true);
    }

    /**
     * Method that creates an instance of Modal class constructor.
     * @param parent the parent JFrame that will be using it.
     * @param title title of the window.
     * @param message message inside the window.
     */
    public static void render(JFrame parent, String title, String message) {
        new Modal(parent, title, message);
    }
}
