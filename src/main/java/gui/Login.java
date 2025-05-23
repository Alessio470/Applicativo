package gui;

import controller.*;

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

    private static JFrame framelogin;

    public Login(){
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

                if () {
                    JOptionPane.showMessageDialog(framelogin, "Login effettuato");
                    JFrame homeFrame = new Home(framelogin,new Controller(username));
                    homeFrame.setVisible(true);
                    framelogin.dispose();
                } else {
                    JOptionPane.showMessageDialog(framelogin, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public static void main(String[] args) {
        framelogin = new JFrame("Home");
        framelogin.setContentPane(new Login().Login);
        framelogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        framelogin.pack();
        framelogin.setSize(625, 270);
        framelogin.setVisible(true);
    }


}
