package gui;

import DAO.UtenteDAO;
import database.ConnessioneDatabase;

import javax.swing.*;
import java.sql.Connection;

public class Registrazione extends JFrame {
    private JPanel PanelRegistrazione;
    private JPanel PanelTitolo;
    private JPanel PanelCampi;
    private JPanel PanelButtons;
    private JLabel LabelTitolo;
    private JPanel PanelUsername;
    private JPanel PanelPassword;
    private JPanel PanelConfermaPassword;
    private JTextField FieldUsername;
    private JLabel LableUsername;
    private JLabel LabelPassword;
    private JLabel LabelConfermaPassword;
    private JPanel PanelButtonRegistrati;
    private JPanel PanelButtonIndietro;
    private JButton ButtonRegistrati;
    private JButton ButtonIndietro;
    private JPasswordField FieldPassword1;   // password
    private JPasswordField FieldPassword2;   // conferma
    private JPanel PanelRuolo;
    private JLabel LabelRuolo;
    private JComboBox ComboRuolo;

    private final JFrame loginFrame;
    private UtenteDAO utenteDAO;

    public Registrazione(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        ComboRuolo.addItem("AMMINISTRATORE");
        ComboRuolo.addItem("GENERICO");
        ComboRuolo.setSelectedItem("GENERICO");

        setTitle("Registrazione");
        setContentPane(PanelRegistrazione);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(900, 550);
        setLocationRelativeTo(loginFrame);

        // Inizializza DAO
        try {
            Connection conn = ConnessioneDatabase.getInstance().getConnection();
            utenteDAO = new UtenteDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore connessione DB:\n" + ex.getMessage());
        }

        // Indietro
        ButtonIndietro.addActionListener(e -> {
            dispose();
            if (loginFrame != null) {
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
            }
        });

        // Registrati
        ButtonRegistrati.addActionListener(e -> onRegistrati());
    }//Parentesi costruttore



}//Parentesi Finale
