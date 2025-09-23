package gui;

import DAO.UtenteDAO;
import controller.Controller;
import database.ConnessioneDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Registrazione {
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

    private Controller controller;


    public static JFrame frame;

    public Registrazione(JFrame loginFrame, Controller controller) {
        this.loginFrame = loginFrame;

        this.controller = controller;

        ComboRuolo.addItem("AMMINISTRATORE");
        ComboRuolo.addItem("GENERICO");
        ComboRuolo.setSelectedItem("GENERICO");

        frame.setTitle("Registrazione");
        frame.setContentPane(PanelRegistrazione);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(loginFrame);

        // Inizializza DAO



        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (loginFrame != null) {
                    loginFrame.setLocationRelativeTo(null);
                    loginFrame.setVisible(true);
                }
            }
        });

        ButtonRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.onRegistrati(FieldUsername.getText().trim(),new String(FieldPassword1.getPassword()), new String(FieldPassword2.getPassword()), ComboRuolo.getSelectedItem().toString(),frame );
            }

        });

    }//Parentesi costruttore




    }//Parentesi Finale
