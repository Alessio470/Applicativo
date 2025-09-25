package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


/**
 * The type Add voli.
*/
public class InserisciVolo {
    private JPanel PanelInserisciVolo;
    private JPanel PanelNomeCompagnia;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelStato;
    private JButton ButtonConferma;
    private JButton ButtonIndietro;
    private JPanel PanelCampiInserimento;
    private JLabel LabelNomeCompagnia;
    private JLabel LabelDataVolo;
    private JLabel LabelOrario;
    private JTextField FieldAeroportoOrigine;
    private JTextField FieldAeroportoDestinazione;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JLabel LabelAeroportoOrigine;
    private JLabel LabelAeroportoDestinazione;
    private JPanel PanelButtons;
    private JPanel PanelButtonConferma;
    private JPanel PanelButtonIndietro;
    private JLabel LabelNGate;
    private JPanel PanelTitolo;
    private JLabel LabelTitolo;

    private JComboBox comboCompagniaAerea;
    private JComboBox ComboStatiVolo;

    private JFormattedTextField FormattedFieldOrario;
    private JFormattedTextField formattedFieldDataVolo;

    public static JFrame frame;



    public InserisciVolo(JFrame prevframe, Controller controller) {


/*
        ComboRuolo.addItem("AMMINISTRATORE");
        ComboRuolo.addItem("GENERICO");
        ComboRuolo.setSelectedItem("GENERICO");

        */

        frame = new JFrame("Inserisci Volo");
        frame.setTitle("Inserisci Volo"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelInserisciVolo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);



// Campo data formato dd/mm/aaaa
        try {
            MaskFormatter dataMask = new MaskFormatter("dd/mm/yyyy");
            dataMask.setPlaceholderCharacter('_');
            formattedFieldDataVolo.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(dataMask)
            );
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

// Campo orario formato hh:mm
        try {
            MaskFormatter oraMask = new MaskFormatter("hh:mm");
            oraMask.setPlaceholderCharacter('_');
            FormattedFieldOrario.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(oraMask)
            );
        } catch (ParseException ex) {
            ex.printStackTrace();
        }



        // Bottone Indietro → torna alla home amministratore
        ButtonIndietro.addActionListener(e -> {
            frame.dispose();
            if (prevframe != null) {
                prevframe.setVisible(true);
                prevframe.toFront();
            }
        });


        ButtonConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            //controller.add //TODO L'inserimento da Controller dei voli
            }
        });


    }//Parentesi Inserisci Voli


    }//Parentesi Finale




