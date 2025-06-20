package gui;

import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The type Home utente generico.
 */
public class HomeUtenteGenerico extends JFrame {
    private JLabel textBenvenuto;
    private JButton buttonExit;
    private JPanel contentPane;
    private JTable tableVoliGenerali;
    private JButton buttonVisualizzaVoliPrenotati;
    private JButton buttonEffettuaPrenotazione;

    private JFrame frame;
    private Controller controller;

    /**
     * Instantiates a new Home utente generico.
     *
     * @param frameChiamante the frame chiamante
     * @param controller     the controller
     */
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
    public void aggiornaTabella() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> listaVoli = controller.getVoli();
        modello.settaVoliDaMostrare(listaVoli);
        tableVoliGenerali.setModel(modello);
    }
}
