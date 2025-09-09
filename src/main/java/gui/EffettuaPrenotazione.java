package gui;

import controller.Controller;
import model.*;

import javax.swing.*;
import java.util.List;

/**
 * The type Effettua prenotazione.
 */

public class EffettuaPrenotazione extends JFrame {
    private JPanel PanelEffettuaPrenotazione;
    private JTable tableVoli;
    private JTextField FieldNome; // Nome
    private JTextField FieldCognome;
    private JTextField FieldCodiceFiscale;
    private JTextField textField3; // Codice fiscale
    private JButton buttonConfermaPrenotazione;
    private JButton buttonIndietro;
    private JLabel LableNome;
    private JLabel LabelCognome;
    private JPanel PanelCodiceFiscale; // Cognome
    private JPanel PanelNumeroPosto;
    private JPanel PanelButtons;
    private JLabel LableCodiceFiscale;
    private JPanel PanelTitolo;
    private JPanel PanelTabellaVoli;
    private JPanel PanelNome;
    private JPanel PanelCognome;
    private JLabel LableNumeroPosto;
    private JTextField FieldNumeroPosto;
    private JPanel PanelBagaglio;
    private JLabel LabelBagaglio;
    private JRadioButton RadioButtonNo;
    private JRadioButton RadioButtonSi;
    private JLabel LabelTitolo;
    private JTable TableVoli;
    private JButton ButtonConfermaPrenotazione;
    private JButton ButtonIndietro;
    private JPanel PanelButtonConfermaPrenotazione;
    private JPanel PanelButtonIndietro;

    private final JFrame homeFrame;

    public EffettuaPrenotazione(JFrame homeFrame) {
        this.homeFrame = homeFrame;

        setTitle("Effettua Prenotazione");
        setContentPane(PanelEffettuaPrenotazione);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(homeFrame);

        ButtonIndietro.addActionListener(e -> {
            dispose();
            if (homeFrame != null) {
                homeFrame.setVisible(true);
                homeFrame.toFront();
            }
        });


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
}


