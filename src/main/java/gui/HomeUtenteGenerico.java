package gui;

import controller.Controller;

import javax.swing.*;

/**
 * The type Home utente generico.
 */


public class HomeUtenteGenerico extends JFrame {
    private JPanel contentPane;

    private JPanel PanelHomeGenerico;
    private JPanel PanelTitolo;
    private JPanel PanelButton;
    private JPanel PanelTabellaVoli;
    private JPanel PanelButtonLogout;
    private JPanel PanelButtonAreaPersonale;
    private JPanel PanelButtonEffettuaPrenotazione;
    private JButton ButtonAereaPersonale;
    private JButton ButtonEffettuaPrenotazione;
    private JTable TableVoli;
    private JButton ButtonLogout;
    private JLabel LabelTitolo;
    private Controller controller;

    public JFrame loginFrame;

    public HomeUtenteGenerico(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("Home Utente Generico");
        setContentPane(PanelHomeGenerico);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(900, 550);
        setLocationRelativeTo(loginFrame);

        ButtonLogout.addActionListener(e -> {
            dispose();
            if (loginFrame != null) {
                loginFrame.setVisible(true);
                loginFrame.toFront();
            }
        });
        ButtonAereaPersonale.addActionListener(e -> {
            AreaPersonale area = new AreaPersonale(this); // "this" è HomeUtenteGenerico
            area.setVisible(true);
            setVisible(false);
        });

        ButtonEffettuaPrenotazione.addActionListener(e -> {
            EffettuaPrenotazione prenotazione = new EffettuaPrenotazione(this);
            prenotazione.setVisible(true);
            setVisible(false);
        });

    }

/*
    public HomeUtenteGenerico(JFrame frameChiamante, Controller controller) {
        this.controller = controller;
        this.frame = new JFrame("Home");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(625, 270);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        textBenvenuto.setText("Benvenuto " + controller.getUsernameGenerico());

        aggiornaTabella();

        buttonExit.addActionListener(e -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });

        buttonVisualizzaVoliPrenotati.addActionListener(e -> {
            JFrame prenotazioniFrame = new PrenotazioniUtente(controller, frame);
            prenotazioniFrame.setVisible(true);
            frame.setVisible(false);
        });

        buttonEffettuaPrenotazione.addActionListener(e -> {
            JFrame finestraPrenota = new EffettuaPrenotazione(controller, frame);
            finestraPrenota.setVisible(true);
            frame.setVisible(false);
        });
    }

    /**
     * Aggiorna tabella.
     */

/*
    public void aggiornaTabella() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> listaVoli = controller.getVoli();
        modello.settaVoliDaMostrare(listaVoli);
        tableVoliGenerali.setModel(modello);
    }
 */
}

