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
    }

    private void onRegistrati() {
        String username = FieldUsername.getText().trim();
        String password = new String(FieldPassword1.getPassword());
        String conferma = new String(FieldPassword2.getPassword());
        String ruolo = ComboRuolo.getSelectedItem().toString(); // prende il ruolo dalla combo

        // --- Validazioni base ---
        if (username.isEmpty() || password.isEmpty() || conferma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi.");
            return;
        }
        if (!password.equals(conferma)) {
            JOptionPane.showMessageDialog(this, "Le password non coincidono.");
            return;
        }
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username troppo corto (min 3).");
            return;
        }
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this, "Password troppo corta (min 4).");
            return;
        }

        try {
            // Controllo unicità username
            if (utenteDAO.usernameExists(username)) {
                JOptionPane.showMessageDialog(this, "Username già in uso.");
                return;
            }

            utenteDAO.registraUtente(username, password, ruolo);

            JOptionPane.showMessageDialog(
                    this,
                    "Registrazione completata!\nUsername: " + username + "\nRuolo: " + ruolo,
                    "OK",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // torna al login
            dispose();
            if (loginFrame != null) {
                loginFrame.setVisible(true);
                loginFrame.toFront();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Errore durante la registrazione:\n" + ex.getMessage());
        }
    }

}
