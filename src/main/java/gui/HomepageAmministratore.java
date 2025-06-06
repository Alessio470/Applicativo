package gui;

import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The type Homepage amministratore.
 */
public class HomepageAmministratore extends JFrame {
    private JLabel labelBenvenuto;
    private JButton buttonInserisciVolo;
    private JButton buttonModificaGate;
    private JButton buttonIndietro;
    private JTable table1;
    private JPanel contentPane;

    private Controller controller;

    /**
     * Instantiates a new Homepage amministratore.
     *
     * @param frameChiamante the frame chiamante
     * @param controller     the controller
     */
    public HomepageAmministratore(JFrame frameChiamante, Controller controller) {
        this.controller = controller;

        setTitle("Homepage Amministratore");
        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(625, 400);
        setLocationRelativeTo(null);

        labelBenvenuto.setText("Benvenuto " + controller.getUsernameAdmin());

        aggiornaTabella();

        buttonInserisciVolo.addActionListener(e -> {
            JFrame addVoloFrame = new AddVoli(controller, this);
            addVoloFrame.setVisible(true);
            this.setVisible(false);
        });

        buttonIndietro.addActionListener(e -> {
            new Login(controller);
            dispose();
        });

        //ToDo
        buttonModificaGate.addActionListener(e -> {

        });

        setVisible(true);
    }

    /**
     * Aggiorna tabella.
     */
    public void aggiornaTabella() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> listaVoli = controller.getVoli();
        modello.settaVoliDaMostrare(listaVoli);
        table1.setModel(modello);
    }


}
