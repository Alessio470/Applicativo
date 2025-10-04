package gui;

import controller.Controller;
import model.Volo;
import model.enums.StatoVolo;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


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
    private JComboBox<String> ComboStatoVolo;
    private JPanel PanelButtons;
    private JPanel PanelButtonConfermaModifica;
    private JPanel PanelButtonLogout;
    private JPanel PanelCompagniaAerea;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelStatoVolo;
    private JFormattedTextField formattedTextFieldData;   // dd/MM/yyyy
    private JFormattedTextField formattedTextFieldOrario; // HH:mm
    private JTextField FieldRitardo;
    private JLabel LabelRitardo;
    private JComboBox comboGate;
    private JLabel LabelGate;
    private JPanel PanelGate;

    private static JFrame frame;

    public HomepageAmministratore(JFrame prevframe, Controller controller) {

        // Inizializza frame
        frame = new JFrame("Frame Home Utente Amministratore");

        frame.setTitle("Home Utente Amministratore"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelHomepageAmministratore);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);

        // Mask data: dd/MM/yyyy
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            formattedTextFieldData.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(dataMask)
            );
        } catch (Exception ignored) {}

        // Mask orario: HH:mm
        try {
            MaskFormatter oraMask = new MaskFormatter("##:##");
            oraMask.setPlaceholderCharacter('_');
            formattedTextFieldOrario.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(oraMask)
            );
        } catch (Exception ignored) {}


//AddItems ComboStatoVolo
        for(StatoVolo stato : StatoVolo.values()) {
            ComboStatoVolo.addItem(stato.toString());
        }

//AddItems ComboStatoVolo

        //AddItems ComboGates
        for(String s : controller.getGates()){
            comboGate.addItem(s);
        }
        //AddItems ComboGates

        // Tabella
        TabellaVoli.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TabellaVoli.setFillsViewportHeight(true);

        this.aggiornaTabella(controller);

        // Pulsanti
        ButtonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (prevframe != null) {
                    prevframe.setLocationRelativeTo(null);
                    prevframe.setVisible(true);
                }
            }
        });//Roba buttonindietro actionlistener

        ButtonInserisciVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InserisciVolo(frame, controller);
                frame.setVisible(false);
            }

        });//Roba ButtonInserisciVolo actionlistener

        ButtonConfermaModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = TabellaVoli.getSelectedRow();
                if (r < 0) {
                    JOptionPane.showMessageDialog(frame, "Seleziona prima una riga della tabella.",
                            "Nessuna riga selezionata", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                try {
                    controller.confermaModifica(TabellaVoli.getValueAt(TabellaVoli.getSelectedRow(), 0).toString(), FieldCompagniaAerea.getText(), FieldAeroportoOrigine.getText(), FieldAeroportoDestinazione.getText(), formattedTextFieldData.getText(), formattedTextFieldOrario.getText(), Integer.parseInt(FieldRitardo.getText()), ComboStatoVolo.getSelectedItem().toString(),comboGate.getSelectedItem().toString());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Errore.","Errore", JOptionPane.WARNING_MESSAGE);
                    throw new RuntimeException(ex);
                }
                aggiornaTabella(controller);
            }
        });//Parentesi ButtonConfermaModifica

        TabellaVoli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int r=TabellaVoli.getSelectedRow();
                if(r>=0){
                    FieldCompagniaAerea.setText(TabellaVoli.getValueAt(r, 1).toString());
                    FieldAeroportoOrigine.setText(TabellaVoli.getValueAt(r, 2).toString());
                    FieldAeroportoDestinazione.setText(TabellaVoli.getValueAt(r, 3).toString());
                    formattedTextFieldData.setText(TabellaVoli.getValueAt(r, 4).toString());
                    formattedTextFieldOrario.setText(TabellaVoli.getValueAt(r, 5).toString());
                    FieldRitardo.setText(TabellaVoli.getValueAt(r, 6).toString());              // ✅ colonna 6 = ritardo
                    ComboStatoVolo.setSelectedItem(TabellaVoli.getValueAt(r, 7).toString());    // ✅ colonna 7 = stato
                    Object gate = TabellaVoli.getValueAt(r, 8);
                    if (gate != null) comboGate.setSelectedItem(gate.toString());

                }

            }
        });//Fine parentesi TabellaVoli MouseListener

    } //Fine parentesi homepageAmministratore

    private void aggiornaTabella(Controller controller) {

        //Mettiamo i dati nell array dei dati che andranno nella tabella
        List<Volo> voli = controller.getVoliDaPerNapoli();
        String[] colonne = {"Codice Volo", "Compagnia", "Origine", "Destinazione", "Data", "Orario", "Ritardo", "Stato", "Gate"};

        // Crea l'array dinamico delle dimensioni giuste
        Object[][] dati = new Object[voli.size()][9]; // 9 colonne come intestazioni

        for (int i = 0; i < voli.size(); i++) {
            Volo v = voli.get(i);
            dati[i][0] = v.getCodiceV();
            dati[i][1] = v.getCompagnia();
            dati[i][2] = v.getAeroportoOrigine();
            dati[i][3] = v.getAeroportoDestinazione();
            dati[i][4] = v.getDataStr();
            dati[i][5] = v.getOrarioStr();
            dati[i][6] = v.getRitardoMinuti();
            dati[i][7] = v.getStato().name();
            dati[i][8] = v.getGate();
        }

// Creiamo il modello e lo impostiamo nella JTable
        javax.swing.table.DefaultTableModel modello = new javax.swing.table.DefaultTableModel(dati, colonne);
        TabellaVoli.setModel(modello);

// Disabilitiamo modifiche dirette
        TabellaVoli.setDefaultEditor(Object.class, null);

// Centriamo tutte le celle

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < TabellaVoli.getColumnCount(); i++) {
            TabellaVoli.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }



    }


}//Parentesi Finale
