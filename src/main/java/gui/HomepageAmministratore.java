package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Homepage amministratore.
 */

public class HomepageAmministratore extends JFrame {
    private JLabel LabelTitolo;
    private JButton ButtonInserisciVolo;
    private JButton ButtonLogout;
    private JTable TabellaVoli;
    private JPanel PanelHomepageAmministratore;
    private JPanel PanelTabellaVoli;
    private JPanel PanelButtonInserisciVolo;
    private JButton ButtonConfermaModifica;
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
    private JPanel PanelButtons;
    private JPanel PanelButtonConfermaModifica;
    private JPanel PanelButtonLogout;
    private JPanel PanelCompagniaAerea;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelStatoVolo;

    private Controller controller;

    public JFrame loginFrame;

    public HomepageAmministratore(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("Home Utente Amministratore");
        setContentPane(PanelHomepageAmministratore);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(900, 550);
        setLocationRelativeTo(loginFrame);

        ButtonLogout.addActionListener(e -> {
            dispose();
            if (loginFrame != null) {
                loginFrame.setVisible(true);
                loginFrame.toFront();
            }
        });

        ButtonInserisciVolo.addActionListener(e -> {
            InserisciVolo inserisci = new InserisciVolo(this);
            inserisci.setVisible(true);
            setVisible(false);
        });
    }

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

