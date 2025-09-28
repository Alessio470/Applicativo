package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;


public class Login {
    private JPanel PanelLogin;
    private JPanel PanelTitolo;
    private JPanel PanelCampi;
    private JPanel PanelButtons;
    private JLabel LabelTitolo;
    private JPanel PanelUsername;
    private JLabel LabelUsername;
    private JTextField FieldUsername;
    private JPanel PanelPassword;
    private JLabel LabelPassword;
    private JPasswordField FieldPassword;
    private JPanel PanelButtonLogin;

    private JPanel PanelOppure;
    private JLabel LabelOppure;
    private JPanel PanelButtonRegistrati;

    private JButton ButtonRegistrati;
    private JButton ButtonLogin;

    public static JFrame frame;
    private Controller controller;


    public Login() {


        controller = new Controller();

        // Listener "Login" → autentica su PostgreSQL e apri la home corretta
        ButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.doLogin(FieldUsername.getText().trim(), new String(FieldPassword.getPassword()), frame);
                frame.setVisible(false);
            }
        });//Parentesi ButtonLogin

        //Listener "Registrati" (placeholder finché non implementi la GUI)
        ButtonRegistrati.addActionListener(new ActionListener() {
                                                       @Override
                                                       public void actionPerformed (ActionEvent e){
                                                           new Registrazione(frame, controller);
                                                           frame.setVisible(false);
                                                       }
                                                   });

    }//Parentesi Login

    public static void main(String[] args) {
        frame = new JFrame("Login");
        frame.setContentPane(new Login().PanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }//Parentesi Main

}//Parentesi Finale


