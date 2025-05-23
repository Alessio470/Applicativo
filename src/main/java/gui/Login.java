package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JPanel Login;
    private JLabel textLogin;
    private JLabel textNome;
    private JLabel textPassword;
    private JTextField FieldNome;
    private JPasswordField FieldPassword;
    private JButton buttonLogin;
    final int carattere = 20;

    private static JFrame frame;

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

                if (username.equals("a") && password.equals("b")) {
                    JOptionPane.showMessageDialog(frame, "Login effettuato");
                    //new Home().setVisible(true);
                   frame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(frame, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Home");
        frame.setContentPane(new Login().Login);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(625, 270);
        frame.setVisible(true);
    }


}
