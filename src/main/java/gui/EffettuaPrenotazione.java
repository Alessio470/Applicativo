package gui;

import controller.Controller;
import model.*;
import model.enums.StatoPrenotazione;
import model.enums.StatoVolo;

import javax.swing.*;
import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Effettua prenotazione.
 */
public class EffettuaPrenotazione extends JFrame {
    private JPanel contentPane;
    private JTable tableVoli;
    private JTextField textField1; // Nome
    private JTextField textField2; // Cognome
    private JTextField textField3; // Codice fiscale
    private JTextField textField4; // Numero posto
    private JButton buttonConfermaPrenotazione;
    private JButton buttonIndietro;

    private Controller controller;
    private JFrame frameChiamante;
    private List<Volo> voliPrenotabili;

    /**
     * Instantiates a new Effettua prenotazione.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
     */
    public EffettuaPrenotazione(Controller controller, JFrame frameChiamante) {
        this.controller = controller;

        this.frameChiamante = frameChiamante;

        this.voliPrenotabili = new ArrayList<>();
        for (Volo v : controller.getVoli()) {
            if (v.getStato() == StatoVolo.PROGRAMMATO) {
                this.voliPrenotabili.add(v);
            }
        }

        setTitle("Effettua Prenotazione");
        setContentPane(contentPane);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        aggiornaTabellaVoli();

        buttonConfermaPrenotazione.addActionListener(e -> {
            int selectedRow = tableVoli.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un volo.");
                return;
            }

            String nome = textField1.getText();
            String cognome = textField2.getText();
            String codiceFiscale = textField3.getText();
            String numeroPosto = textField4.getText();

            if (nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty() || numeroPosto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi.");
                return;
            }

            VoloPartenzaDaNapoli volo = (VoloPartenzaDaNapoli) voliPrenotabili.get(selectedRow);

            String numeroBiglietto = "TCK-" + UUID.randomUUID().toString().substring(0, 6);

            Prenotazione prenotazione = new Prenotazione(
                    numeroBiglietto,
                    codiceFiscale,
                    nome,
                    cognome,
                    numeroPosto,
                    StatoPrenotazione.CONFERMATA,
                    volo
            );

            controller.getPrenotazioniUtenteGenerico().add(prenotazione);

            JOptionPane.showMessageDialog(this, "Prenotazione effettuata con successo.");
            frameChiamante.setVisible(true);
            dispose();
        });

        buttonIndietro.addActionListener(e -> {
            frameChiamante.setVisible(true);
            dispose();
        });
    }

    private void aggiornaTabellaVoli() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> filtrati = new ArrayList<>();

        for (Volo v : controller.getVoli()) {
            if (v instanceof VoloPartenzaDaNapoli && v.getStato() == StatoVolo.PROGRAMMATO) {
                filtrati.add(v);
            }
        }

        modello.settaVoliDaMostrare(filtrati);
        tableVoli.setModel(modello);
    }
}
