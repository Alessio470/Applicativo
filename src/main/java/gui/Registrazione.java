package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra di registrazione utente.
 *
 * <p>Consente l'inserimento di username, password e ruolo, e delega la creazione al {@link controller.Controller}.
 *
 * @see controller.Controller
 */
public class Registrazione {
    private JPanel PanelRegistrazione;
    private JPanel PanelTitolo;
    private JPanel PanelCampi;
    private JPanel PanelButtons;
    private JPanel PanelUsername;
    private JPanel PanelPassword;
    private JPanel PanelConfermaPassword;
    private JPanel PanelButtonRegistrati;
    private JPanel PanelRuolo;
    private JPanel PanelButtonIndietro;
    private JTextField FieldUsername;
    private JPasswordField FieldPassword1;   // password
    private JPasswordField FieldPassword2;   // conferma
    private JButton ButtonRegistrati;
    private JButton ButtonIndietro;
    private JComboBox ComboRuolo;
    private JLabel LabelTitolo;
    private JLabel LableUsername;
    private JLabel LabelPassword;
    private JLabel LabelConfermaPassword;
    private JLabel LabelRuolo;

    private JFrame frame;

    /**
     * Costruisce e visualizza la finestra di registrazione.
     *
     * <p>Inizializza la GUI, precompila la combo del ruolo (default GENERICO) e registra i listener:
     *
     * <p>- Indietro: chiude e torna alla finestra precedente.
     * <br>- Registrati: valida campi a cura del {@link controller.Controller} e invoca
     *   {@link controller.Controller#onRegistrati(String, String, String, String, JFrame)}.
     *
     * @param prevframe finestra chiamante a cui ritornare
     * @param controller controller applicativo che gestisce la registrazione
     */
    public Registrazione(JFrame prevframe, Controller controller) {

        ComboRuolo.addItem("AMMINISTRATORE");
        ComboRuolo.addItem("GENERICO");
        ComboRuolo.setSelectedItem("GENERICO");

        frame = new JFrame("Registrazione");
        frame.setTitle("Registrazione"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelRegistrazione);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);

        // Inizializza DAO

        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (prevframe != null) {
                    prevframe.setLocationRelativeTo(null);
                    prevframe.setVisible(true);
                }
            }
        });

        ButtonRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                controller.onRegistrati(FieldUsername.getText().trim(),new String(FieldPassword1.getPassword()), new String(FieldPassword2.getPassword()), ComboRuolo.getSelectedItem().toString(),frame );

            }

        });

    }//Parentesi costruttore

}//Parentesi Finale
