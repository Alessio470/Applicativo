package gui;

import DAO.UtenteDAO;
import controller.Controller;
import database.ConnessioneDatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class Registrazione { //HO CAMBIATO LA ROBA DA EXTEND JFRAME L HO TOLTA
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




    private JFrame frame; //TODO PER LA ROBA DELL EXTEND HO MESSO STO FRAME

    public Registrazione(JFrame prevframe, Controller controller) {

        ComboRuolo.addItem("AMMINISTRATORE");
        ComboRuolo.addItem("GENERICO");
        ComboRuolo.setSelectedItem("GENERICO");

        frame = new JFrame("Registrazione");
        frame.setTitle("Registrazione"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelRegistrazione);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);


        // Inizializza DAO



        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (prevframe != null) {
                    prevframe.setLocationRelativeTo(null);
                    prevframe.setVisible(true);
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
