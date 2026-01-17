package gui;

import controller.Controller;
import model.enums.RuoloUtente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


/**
 * Schermata di login dell'applicazione.
 *
 * <p>Gestisce l’autenticazione tramite {@link controller.Controller} e l’accesso alla registrazione.
 *
 * @see controller.Controller
 */
public class Login {
    private JPanel PanelLogin;
    private JPanel PanelTitolo;
    private JPanel PanelCampi;
    private JPanel PanelButtons;
    private JPanel PanelUsername;
    private JPanel PanelOppure;
    private JPanel PanelButtonRegistrati;
    private JPanel PanelButtonLogin;
    private JPanel PanelPassword;
    private JTextField FieldUsername;
    private JPasswordField FieldPassword;
    private JButton ButtonRegistrati;
    private JButton ButtonLogin;
    private JLabel LabelOppure;
    private JLabel LabelUsername;
    private JLabel LabelPassword;
    private JLabel LabelTitolo;

    /**
     * Frame principale della schermata di login.
     */
    public static JFrame frame;
    private Controller controller;


    /**
     * Inizializza la schermata di login.
     *
     * <p>Il pulsante Login delega l’autenticazione al {@link controller.Controller},
     * valuta l’esito restituito e, in base al ruolo dell’utente autenticato,
     * apre la schermata home appropriata.</p>
     *
     * <p>Il pulsante Registrati apre la finestra di registrazione
     * mantenendo la gestione della navigazione all’interno della GUI.</p>
     */
    public Login() {
        controller = new Controller();

        // Listener "Login" → autentica su PostgreSQL e apri la home corretta
        ButtonLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String username = FieldUsername.getText().trim();
                String password = new String(FieldPassword.getPassword());

                if (username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Inserisci username e password.");
                    return;
                }

                try {
                    RuoloUtente ruolo = controller.doLogin(username, password);

                    if (ruolo == null) {
                        JOptionPane.showMessageDialog(frame, "Credenziali non valide!");
                        return;
                    }

                    if (ruolo == RuoloUtente.AMMINISTRATORE) {
                        new HomepageAmministratore(frame, controller);
                        frame.setVisible(false);
                    } else if (ruolo == RuoloUtente.GENERICO) {
                        new HomeUtenteGenerico(frame, controller);
                        frame.setVisible(false);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ruolo utente non riconosciuto.");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(frame, "Errore Database:\n" + ex.getMessage());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore imprevisto:\n" + ex.getMessage());
                }
            }
        });


        //Listener "Registrati" (placeholder finché non implementi la GUI)
        ButtonRegistrati.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                new Registrazione(frame, controller);
                frame.setVisible(false);
            }
        });

    }//Parentesi Login

    /**
     * Entry point dell'applicazione.
     *
     * <p>Crea e visualizza il frame di login.
     *
     * @param args argomenti da riga di comando (non utilizzati)
     */
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


