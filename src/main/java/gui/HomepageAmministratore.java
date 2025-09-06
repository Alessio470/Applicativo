package gui;

import controller.Controller;

import javax.swing.*;

/**
 * The type Homepage amministratore.
 */

public class HomepageAmministratore extends JFrame {
    private JLabel labelBenvenuto;
    private JButton BottoneInserisciVolo;
    private JButton buttonIndietro;
    private JTable TabellaVoli;
    private JPanel PanelHomepageAmministratore;
    private JPanel PanelTabellaVoli;
    private JPanel PanelBottoni;
    private JButton CONFERMAMODIFICAButton;
    private JPanel PanelTitolo;
    private JPanel PanelCampiModifica;
    private JLabel LableCompagniaAerea;
    private JTextField FieldCompagniaAerea;
    private JLabel LableAeroportoOrigine;
    private JTextField FieldAeroportoOrigine;
    private JLabel LabelAeroporotoDestinazione;
    private JTextField FieldAeroportoDestinazione;
    private JLabel LabelDataVolo;
    private JTextField FieldDataVolo;
    private JLabel LabelOrario;
    private JTextField FieldOrario;
    private JLabel LabelStatoVolo;
    private JComboBox ComboStatoVolo;

    private Controller controller;

    /**
     * Instantiates a new Homepage amministratore.
     *
     * @param frameChiamante the frame chiamante
     * @param controller     the controller
     */

/*
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

/*
    public void aggiornaTabella() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> listaVoli = controller.getVoli();
        modello.settaVoliDaMostrare(listaVoli);
        table1.setModel(modello);
    }

 */

}

