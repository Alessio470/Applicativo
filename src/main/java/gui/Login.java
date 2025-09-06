package gui;

import controller.Controller;
import model.enums.RuoloUtente;

import javax.swing.*;

public class Login extends JFrame {
    private JPanel login;
    private JPanel Titolo;
    private JPanel CampiRegistrazione;
    private JPanel FrameUsername;
    private JPanel FramePassword;
    private JLabel TextEffettuaAccesso;
    private JLabel TextUsername;
    private JTextField FieldUsername;
    private JLabel TextPassword;
    private JTextField FieldPassword;
    private JButton BottoneLogin;
    private JButton BottoneRegistrati;
    private JLabel LabelOppire;
    private JPanel PanelLogin;
    private JPanel PanelRegistrati;
    private JPanel PanelOppure;
    private JLabel textLogin;
    private JLabel textNome;
    private JLabel textPassword;
    private JTextField fieldNome;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
    /**
     * The Carattere.
     */
    final int carattere = 20;
    private Controller controller;

    public Login(Controller controller) {
        this.controller = controller;

        setTitle("Login");
        setContentPane(login);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(625, 270);
        setLocationRelativeTo(null);

        textLogin.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        fieldNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        fieldPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));

        setVisible(true);

        buttonLogin.addActionListener(e -> {
            String username = fieldNome.getText();
            String password = new String(fieldPassword.getPassword());

            if (controller.login(username, password)) {
                JOptionPane.showMessageDialog(this, "Login effettuato");

                RuoloUtente ruolo = controller.getUtenteLoggato().getRuolo();

                /*
                switch (ruolo) {
                    case UTENTE -> {
                        JFrame homeFrame = new HomeUtenteGenerico(this, controller);
                        homeFrame.setVisible(true);
                        this.dispose();
                    }
                    case AMMINISTRATORE -> {
                        JFrame homeAdmin = new HomepageAmministratore(this, controller);
                        homeAdmin.setVisible(true);
                        this.dispose();
                    }
                    default -> JOptionPane.showMessageDialog(this, "Tipo utente sconosciuto");
                }

                 */

            } else {
                JOptionPane.showMessageDialog(this, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}