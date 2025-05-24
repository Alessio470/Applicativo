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
    private static JFrame framelogin;

    public Login(Controller controller) {
        this.controller = controller;

        framelogin = new JFrame("Login");
        framelogin.setContentPane(login);
        framelogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framelogin.pack();
        framelogin.setSize(625, 270);
        framelogin.setVisible(true);

        textLogin.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        fieldNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        fieldPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = fieldNome.getText();
                String password = new String(fieldPassword.getPassword());

                if (controller.loginValido(username, password)) {
                    JOptionPane.showMessageDialog(framelogin, "Login effettuato");
                    switch (controller.userType()) {
                        case "Generico":
                            JFrame homeFrame = new Home(framelogin, controller);
                            homeFrame.setVisible(true);
                            framelogin.dispose();
                            break;
                        case "Admin":
                            JFrame homeAdmin = new HomepageAmministratore(framelogin, controller);
                            homeAdmin.setVisible(true);
                            framelogin.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(framelogin, "Tipo utente sconosciuto");
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(framelogin, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
