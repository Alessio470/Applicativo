package gui;

import controller.Controller;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * The type Homepage amministratore.
 */

public class HomepageAmministratore  {
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
    private JLabel LabelOrario;
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
    private JFormattedTextField formattedTextFieldData;
    private JFormattedTextField formattedTextFieldOrario;

    private Controller controller;

    private static JFrame frame;
    public JFrame prevframe;

    public HomepageAmministratore(JFrame prevframe, Controller controller) {
        this.prevframe = prevframe;

        frame = new JFrame("Panel Amministratore");
        frame.setTitle("Home Utente Amministratore"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelHomepageAmministratore);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);

        // Campo data formato dd/mm/aaaa
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            formattedTextFieldData.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(dataMask)
            );
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

// Campo orario formato hh:mm
        try {
            MaskFormatter oraMask = new MaskFormatter("##:##");
            oraMask.setPlaceholderCharacter('_');
            formattedTextFieldOrario.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(oraMask)
            );
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        ButtonLogout.addActionListener(e -> {
            frame.dispose();
            if (prevframe != null) {
                prevframe.setVisible(true);
                prevframe.toFront();
            }
        });

        ButtonInserisciVolo.addActionListener(e -> {
            InserisciVolo inserisci = new InserisciVolo(frame, controller);
            frame.setVisible(false);
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

