package gui;

import controller.Controller;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomepageAmministratore extends JFrame {
    private JLabel labelBenvenuto;
    private JButton buttonInserisciVolo;
    private JButton buttonAggiornaVolo;
    private JTable table1;
    private JPanel contentPane;

    private Controller controller;

    public HomepageAmministratore(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        // ✅ Usa direttamente this invece di un campo frame
        setTitle("Homepage Amministratore");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(625, 400);
        setVisible(true);

        String[] colonne = {
                "Codice Volo", "Compagnia", "Data", "Orario Previsto", "Ritardo", "Stato"
        };

        labelBenvenuto.setText("Benvenuto " + controller.getUsernameAdmin());

        DefaultTableModel model = new DefaultTableModel(colonne, 0);

        // Popola il modello con i dati dei voli
        for (Volo v : controller.getVoli()) {
            Object[] riga = {
                    v.getCodiceVolo(),
                    v.getCompagniaAerea(),
                    v.getDataVolo(),
                    v.getOrarioPrevisto(),
                    v.getRitardo(),
                    (v.getStato() != null ? v.getStato().toString() : "N/A")
            };
            model.addRow(riga);
        }

// Imposta il modello alla JTable
        table1.setModel(model);

        buttonInserisciVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creazione manuale di un nuovo volo (esempio statico)
                //TODO controller che chiama un altra mini gui con inserisci dati
                // VoloPartenzaDaNapoli nuovoVolo =
                );

                //controller.aggiungiVolo(nuovoVolo);
                JOptionPane.showMessageDialog(HomepageAmministratore.this, "Volo inserito con successo.");
            }
        });

        buttonAggiornaVolo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funzionalità aggiornamento volo in arrivo...");
        });
    }
}
