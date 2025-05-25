package gui;

import controller.Controller;
import model.Volo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomepageAmministratore extends JFrame {
    private JLabel labelBenvenuto;
    private JButton buttonInserisciVolo;
    private JButton buttonAggiornaVolo;
    private JTable table1;
    private JPanel contentPane;
    private JButton buttonIndietro;

    private Controller controller;

    public HomepageAmministratore(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        setTitle("Homepage Amministratore");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(625, 400);
        setLocationRelativeTo(null); // Centra la finestra

        labelBenvenuto.setText("Benvenuto " + controller.getUsernameAdmin());

        aggiornaTabella(); // Prima inizializzazione tabella

        buttonInserisciVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addVoloFrame = new AddVoli(controller, HomepageAmministratore.this);
                addVoloFrame.setVisible(true);
                HomepageAmministratore.this.setVisible(false);
            }

        });

        buttonAggiornaVolo.addActionListener(e -> {
            aggiornaTabella();
            JOptionPane.showMessageDialog(this, "Tabella aggiornata.");
        });

        buttonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(controller); // riapre la schermata di login
                dispose();
            }
        });

        setVisible(true);
    }

    public void aggiornaTabella() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> listaVoli = controller.getVoli();
        modello.settaVoliDaMostrare(listaVoli);
        table1.setModel(modello);
    }
}
