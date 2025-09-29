package gui;

import controller.Controller;

import javax.swing.*;

/**
 * The type Home utente generico.
 */


public class HomeUtenteGenerico {
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


    private static JFrame frame;

    public HomeUtenteGenerico(JFrame prevframe, Controller controller) {


        frame = new JFrame("Panel Utente Generico");
        frame.setTitle("Home Utente Generico"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelHomeGenerico);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);
        

        ButtonLogout.addActionListener(e -> {
            frame.dispose();
            if (prevframe != null) {
                prevframe.setVisible(true);
                prevframe.toFront();
            }
        });
        ButtonAereaPersonale.addActionListener(e -> {
            AreaPersonale area = new AreaPersonale(frame,controller); // "this" è HomeUtenteGenerico
            frame.setVisible(false);
        });

        ButtonEffettuaPrenotazione.addActionListener(e -> {
            new EffettuaPrenotazione(frame,controller);
            frame.setVisible(false);
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

