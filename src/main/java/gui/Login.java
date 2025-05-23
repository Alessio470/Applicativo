package gui;

import javax.swing.*;

public class Login {
    private JPanel Login;
    private JLabel textLogin;
    private JLabel textNome;
    private JLabel textPassword;
    private JTextField FieldNome;
    private JPasswordField FieldPassword;
    final int carattere = 20;

    private static JFrame frame;

    public Login(){
        textLogin.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        textPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        FieldNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
        FieldPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, carattere));
    }

    public static void main(String[] args) {
        frame = new JFrame("Home");
        frame.setContentPane(new Login().Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 250);
        frame.setVisible(true);
    }


}
