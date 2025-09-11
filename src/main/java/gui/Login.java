package gui;

import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import DAO.UtenteDAO;
import database.ConnessioneDatabase;
import model.Utente;
import model.enums.RuoloUtente;
import java.sql.Connection;

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
    private JButton ButtonLogin;
    private JPanel PanelOppure;
    private JLabel LabelOppure;
    private JPanel PanelButtonRegistrati;
    private JButton ButtonRegistrati;

    public static JFrame frame;

    private UtenteDAO utenteDAO;

    public Login() {
        // 1) Inizializza DAO collegandoti al DB
        try {
            Connection conn = database.ConnessioneDatabase.getInstance().getConnection();
            utenteDAO = new DAO.UtenteDAO(conn);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Errore connessione DB:\n" + ex.getMessage());
        }


        // 2) Listener "Registrati" (placeholder finché non implementi la GUI)
        ButtonRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Registrazione reg = new Registrazione(frame);
                reg.setVisible(true);
                frame.setVisible(false);
            }
        });

        // 3) Listener "Login" → autentica su PostgreSQL e apri la home corretta
        ButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });
    }

    private void doLogin() {
        String user = FieldUsername.getText().trim();
        String pass = new String(FieldPassword.getPassword()); // da JPasswordField

        try {
            Utente u = utenteDAO.login(user, pass);
            if (u == null) {
                JOptionPane.showMessageDialog(frame, "Credenziali non valide");
                return;
            }

            if (u.getRuolo() == RuoloUtente.AMMINISTRATORE) {
                HomepageAmministratore admin = new HomepageAmministratore(frame);
                admin.setLocationRelativeTo(frame);
                admin.setVisible(true);
                frame.setVisible(false);
            } else {
                HomeUtenteGenerico home = new HomeUtenteGenerico(frame);
                home.setLocationRelativeTo(frame);
                home.setVisible(true);
                frame.setVisible(false);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore durante il login:\n" + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        frame = new JFrame("Login");
        frame.setContentPane(new Login().PanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}