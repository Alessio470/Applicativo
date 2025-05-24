package gui;

import controller.Controller;
import model.VoloPartenzaDaNapoli;
import model.enums.StatoVolo;

import javax.swing.*;
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

        labelBenvenuto.setText("Benvenuto " + controller.getUsernameAdmin());

        buttonInserisciVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VoloPartenzaDaNapoli nuovoVolo = new VoloPartenzaDaNapoli(
                        "NAP999", "Lufthansa", "2025-05-10", "18:00", "0 minuti",
                        StatoVolo.programmato, "Milano Linate"
                );
                controller.aggiungiVolo(nuovoVolo);
                JOptionPane.showMessageDialog(HomepageAmministratore.this, "Volo inserito con successo.");
            }
        });

        buttonAggiornaVolo.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Funzionalità aggiornamento volo in arrivo...");
        });
    }
}
