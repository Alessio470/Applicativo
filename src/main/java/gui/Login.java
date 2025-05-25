package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel login;
    private JLabel textLogin;
    private JLabel textNome;
    private JLabel textPassword;
    private JTextField fieldNome;
    private JPasswordField fieldPassword;
    private JButton buttonLogin;
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

            if (controller.loginValido(username, password)) {
                JOptionPane.showMessageDialog(this, "Login effettuato");

                switch (controller.userType()) {
                    case "Generico":
                        JFrame homeFrame = new HomeUtenteGenerico(this, controller);
                        homeFrame.setVisible(true);
                        this.dispose();
                        break;
                    case "Admin":
                        JFrame homeAdmin = new HomepageAmministratore(this, controller);
                        homeAdmin.setVisible(true);
                        this.dispose();
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Tipo utente sconosciuto");
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(this, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

