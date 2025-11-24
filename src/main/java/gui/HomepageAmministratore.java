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


/**
 * Home dell'utente amministratore.
 *
 * <p>Mostra la tabella dei voli e consente inserimento, modifica e aggiornamento di campi
 * quali compagnia, aeroporti, data, orario, stato, gate e ritardo.
 *
 * @see controller.Controller
 * @see model.Volo
 * @see model.enums.StatoVolo
 */
public class HomepageAmministratore  {
    private JPanel PanelButtons;
    private JPanel PanelButtonConfermaModifica;
    private JPanel PanelButtonLogout;
    private JPanel PanelCompagniaAerea;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelStatoVolo;
    private JPanel PanelTabellaVoli;
    private JPanel PanelButtonInserisciVolo;
    private JPanel PanelTitolo;
    private JPanel PanelGate;
    private JPanel PanelRitardo;
    private JPanel PanelHomepageAmministratore;
    private JPanel PanelCampiModifica;
    private JTable TabellaVoli;
    private JTextField FieldRitardo;
    private JTextField FieldCompagniaAerea;
    private JTextField FieldAeroportoOrigine;
    private JTextField FieldAeroportoDestinazione;
    private JFormattedTextField formattedTextFieldData;   // dd/MM/yyyy
    private JFormattedTextField formattedTextFieldOrario; // HH:mm
    private JButton ButtonInserisciVolo;
    private JButton ButtonLogout;
    private JButton ButtonConfermaModifica;
    private JButton ButtonAggiorna;
    private JComboBox comboGate;
    private JComboBox<String> ComboStatoVolo;
    private JLabel LableAeroportoOrigine;
    private JLabel LabelAeroporotoDestinazione;
    private JLabel LabelTitolo;
    private JLabel LabelDataVolo;
    private JLabel LabelOrario;
    private JLabel LabelStatoVolo;
    private JLabel LableCompagniaAerea;
    private JLabel LabelRitardo;
    private JLabel LabelGate;

    private static JFrame frame;
    private final Controller controller;

    /**
     * Costruisce e visualizza la home amministratore.
     *
     * <p>Inizializza la finestra, applica maschere di input (data dd/MM/yyyy, orario HH:mm),
     * carica valori nelle combo (stati volo, gate), configura la tabella con ordinamento
     * e sola lettura, e registra i listener dei pulsanti:
     *
     * <p>- Logout: chiude la finestra e termina la sessione.
     * <br>- Inserisci volo: apre la finestra di inserimento e nasconde la home.
     * <br>- Conferma modifica: legge i campi e invoca {@link controller.Controller#confermaModifica(String, String, String, String, String, String, int, String, String)}.
     * <br>- Aggiorna: ricarica i dati mantenendo, se possibile, la riga selezionata.
     *
     * @param prevframe  finestra chiamante a cui ritornare
     * @param controller controller applicativo per operazioni su voli e lookup di gate
     */
    public HomepageAmministratore(JFrame prevframe, Controller controller) {
        // Inizializza frame
        frame = new JFrame("Frame Home Utente Amministratore");
        this.controller = controller;

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
        for(String s : controller.getGates()) {
            comboGate.addItem(s);
        }
        //AddItems ComboGates

        // Tabella
        TabellaVoli.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TabellaVoli.setFillsViewportHeight(true);



        this.aggiornaTabella(controller);

        TabellaVoli.setAutoCreateRowSorter(true);

        // Pulsanti
        ButtonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                frame.dispose();
                controller.doLogoutUser();
                if (prevframe != null) {
                    prevframe.setLocationRelativeTo(null);
                    prevframe.setVisible(true);
                }
            }
        });//Roba buttonindietro actionlistener

        ButtonInserisciVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InserisciVolo(frame, controller, HomepageAmministratore.this);
                frame.setVisible(false);
            }

        });//Roba ButtonInserisciVolo actionlistener

        ButtonConfermaModifica.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int r = TabellaVoli.getSelectedRow();
                if (r < 0) {
                    JOptionPane.showMessageDialog(frame, "Seleziona prima una riga della tabella.", "Nessuna riga selezionata", JOptionPane.WARNING_MESSAGE);
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
                    FieldRitardo.setText(TabellaVoli.getValueAt(r, 6).toString());
                    ComboStatoVolo.setSelectedItem(TabellaVoli.getValueAt(r, 7).toString());
                    Object gate = TabellaVoli.getValueAt(r, 8);
                    if (gate != null) comboGate.setSelectedItem(gate.toString());
                }
            }
        });//Fine parentesi TabellaVoli MouseListener

        ButtonAggiorna.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int prevRow = TabellaVoli.getSelectedRow(); // opzionale: prova a mantenere la selezione
                aggiornaTabella(controller);
                if (prevRow >= 0 && prevRow < TabellaVoli.getRowCount()) {
                    TabellaVoli.setRowSelectionInterval(prevRow, prevRow);
                }
            }
        });

    } //Fine parentesi homepageAmministratore

    /**
     * Popola/aggiorna la tabella dei voli con i dati forniti dal {@link controller.Controller}.
     *
     * <p>Colonne: Codice Volo, Compagnia, Origine, Destinazione, Data, Orario, Ritardo, Stato, Gate.
     *
     * <p>Imposta il modello, disabilita l’editing delle celle e centra il rendering.
     *
     * @param controller controller da cui recuperare la lista ordinata di voli
     */
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
    }//Fine parentesi aggiornaTabella

}//Parentesi Finale
