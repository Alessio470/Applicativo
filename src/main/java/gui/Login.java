package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login extends JFrame {
    private JPanel Login;
    private JLabel textLogin;
    private JLabel textNome;
    private JLabel textPassword;
    private JTextField FieldNome;
    private JPasswordField FieldPassword;
    private JButton buttonLogin;
    final int carattere = 20;


    private Controller controller;

    private static JFrame framelogin;

    public Login(Controller controller){
        this.controller = controller;

        framelogin = new JFrame("Login");
        framelogin.setContentPane(Login);
        framelogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framelogin.pack();
        framelogin.setSize(625, 270);
        framelogin.setVisible(true);

        textLogin.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        FieldNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        FieldPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));

        buttonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = FieldNome.getText();
                String password = new String(FieldPassword.getPassword());

                if (Controller.loginValido(username, password)) {
                    JOptionPane.showMessageDialog(framelogin, "Login effettuato");
                    JFrame homeFrame = new Home(framelogin,controller);
                    homeFrame.setVisible(true);
                    framelogin.dispose();
                } else {
                    JOptionPane.showMessageDialog(framelogin, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }



}
