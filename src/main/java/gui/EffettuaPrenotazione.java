package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.util.List;

/**
 * The type Effettua prenotazione.
 */

public class EffettuaPrenotazione extends JFrame {
    private JPanel FrameEffettuaPrenotazione;
    private JTable tableVoli;
    private JTextField FieldNome; // Nome
    private JTextField FieldCognome;
    private JTextField FieldCodiceFiscale;
    private JTextField textField3; // Codice fiscale
    private JButton buttonConfermaPrenotazione;
    private JButton buttonIndietro;
    private JLabel LableNome;
    private JLabel LabelCognome;
    private JPanel PanelCampoCodiceFiscale; // Cognome
    private JPanel PanelCampoNumeroPosto;
    private JPanel PanelBottoni;
    private JLabel LableCoficeFiscale;
    private JPanel PanelTitolo;
    private JPanel PanelTabellaVoli;
    private JPanel PanelCampoNome;
    private JPanel PanelCampoCognome;
    private JLabel LableNumeroPosto;
    private JTextField FieldNumeroPosto;
    private JPanel PanelBagaglio;
    private JLabel LabelBagaglio;
    private JRadioButton RadioButtonNo;
    private JRadioButton RadioButtonSI;

    private Controller controller;
    private JFrame frameChiamante;
    private List<Volo> voliPrenotabili;

    /**
     * Instantiates a new Effettua prenotazione.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
     */
/*
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

            controller.aggiungiPrenotazione(prenotazione);

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
*/
}


