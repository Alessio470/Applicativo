package gui;

import controller.Controller;
import model.*;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JLabel textBenvenuto;
    private JButton buttonExit;
    private JPanel contentPane;
    private JTable tablePrenotazioni;
    private JList listPrenotazioni;

    public JFrame frame;

    public Home(JFrame frameChiamante, Controller controller) {
        frame = new JFrame("Home");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(625,270);
        textBenvenuto.setText("Benvenuto " + controller.getUsernameGenerico());

        // Definizione delle colonne della tabella
        String[] colonne = {
                "Biglietto", "Nome", "Cognome", "Codice Fiscale",
                "Posto", "Stato", "Codice Volo"
        };

        // Modello della tabella
        DefaultTableModel model = new DefaultTableModel(colonne, 0);

        for (Prenotazione p : controller.getPrenotazioniUtenteGenerico()) {
            Volo v = p.getVolo(); // Prendi il volo associato alla prenotazione

            Object[] riga = {
                    p.getNumeroBiglietto(),
                    p.getNomePasseggero(),
                    p.getCognomePasseggero(),
                    p.getCodiceFiscalePasseggero(),
                    p.getNumeroPosto(),
                    p.getStatoPrenotazione().toString(),
                    (v != null ? v.getCodiceVolo() : "N/A")
            };

            model.addRow(riga);
        }

        tablePrenotazioni.setModel(model);


        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
}
