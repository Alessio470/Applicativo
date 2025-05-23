package gui;

import javax.swing.*;

public class Home {
    private JPanel Login;
    private JLabel textLogin;
    private JLabel textNome;
    private JLabel textPassword;
    private JTextField FieldNome;
    private JPasswordField FieldPassword;

    private static JFrame frame;

    public Home(){
        textLogin.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        textNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        textPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        FieldNome.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
        FieldPassword.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 30));
    }

    public static void main(String[] args) {
        frame = new JFrame("Home");
        frame.setContentPane(new Home().Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 600);
        frame.setVisible(true);
    }


}
