package gui;

import controller.Controller;
import model.Volo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The type Effettua prenotazione.
 */
public class EffettuaPrenotazione {
    private JPanel PanelEffettuaPrenotazione;
    private JTable tableVoli;
    private JTextField FieldNome; // Nome
    private JTextField FieldCognome;
    private JTextField FieldCodiceFiscale;
    private JTextField textField3; // Codice fiscale
    private JButton buttonConfermaPrenotazione;
    private JButton buttonIndietro;
    private JLabel LableNome;
    private JLabel LabelCognome;
    private JPanel PanelCodiceFiscale; // Cognome
    private JPanel PanelNumeroPosto;
    private JPanel PanelButtons;
    private JLabel LableCodiceFiscale;
    private JPanel PanelTitolo;
    private JPanel PanelTabellaVoli;
    private JPanel PanelNome;
    private JPanel PanelCognome;
    private JLabel LableNumeroPosto;
    private JTextField FieldNumeroPosto;
    private JPanel PanelBagaglio;
    private JLabel LabelBagaglio;
    private JRadioButton RadioButtonNo;
    private JRadioButton RadioButtonSi;
    private JLabel LabelTitolo;
    private JTable TableVoli;
    private JButton ButtonConfermaPrenotazione;
    private JButton ButtonIndietro;
    private JPanel PanelButtonConfermaPrenotazione;
    private JPanel PanelButtonIndietro;

    private static JFrame frame;

    /**
     * Instantiates a new Effettua prenotazione.
     *
     * @param prevframe  the prevframe
     * @param controller the controller
     */
    public EffettuaPrenotazione(JFrame prevframe, Controller controller) {


        frame = new JFrame("Panel Effettua Prenotazione");
        frame.setTitle("Effettua Prenotazione"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelEffettuaPrenotazione);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);



// Intestazioni colonne
        String[] colonne = {"Codice Volo", "Compagnia", "Origine", "Destinazione", "Data", "Orario", "Ritardo", "Stato", "Gate"};

// Dati fittizi
        /*
       Object[][] dati = {
    {"AZ123", "Alitalia", "Napoli", "Roma", "27/09/2025", "14:30", 0, "PROGRAMMATO", "A1"},
    {"FR456", "Ryanair", "Napoli", "Milano", "27/09/2025", "16:00", 15, "IN_RITARDO", "B2"},
    {"DL789", "Delta", "Napoli", "Parigi", "28/09/2025", "09:45", 0, "PROGRAMMATO", "C3"}
};*/


        //Mettiamo i dati nell array dei dati che andranno nella tabella
        List<Volo> voli = controller.getVoliPrenotabili();

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
        TableVoli.setModel(modello);

        //Tabella in ordine
        TableVoli.setAutoCreateRowSorter(true);


// Disabilitiamo modifiche dirette
        TableVoli.setDefaultEditor(Object.class, null);

// Centriamo tutte le celle

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < TableVoli.getColumnCount(); i++) {
            TableVoli.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }



        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (prevframe != null) {
                    prevframe.setLocationRelativeTo(null);
                    prevframe.setVisible(true);
                }
            }
        });//Roba buttonindietro actionlistener


        ButtonConfermaPrenotazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //public Prenotazione(String numeroBiglietto,String usernameUtente, String codiceVolo,
                // String nomePasseggero,String cognomePasseggero,String posto, StatoPrenotazione stato, String codicefiscalepasseggero)

                int selectedRow = TableVoli.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(frame, "Seleziona un volo.");
                    return;
                }

// codice volo è nella colonna 0
                // Codice volo è nella colonna 0
                String codiceVolo = (String) TableVoli.getValueAt(selectedRow, 0);
                controller.effettuaPrenotazione((String) TableVoli.getValueAt(selectedRow, 0),FieldNome.getText().trim(),FieldCognome.getText().trim(),FieldNumeroPosto.getText().trim(),FieldCodiceFiscale.getText().trim());
                //TODO controllare perchè effettua prenotazione non funziona e da Nessun risultato è stato restituito dalla query.
                frame.dispose();
                if (prevframe != null) {
                    prevframe.setLocationRelativeTo(null);
                    prevframe.setVisible(true);
                }

            }//parentesi action performed
        });//Fine parentesi buttonConferma

/*
        TableVoli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                // Ottieni l'indice della riga cliccata
                //int rowIndex = TableVoli.rowAtPoint(e.getPoint());
                //System.out.println("Riga cliccata: " + rowIndex);

                //Semplicemente:
                //Volo v = voli.get(rowIndex);

                // Leggi tutti i valori della riga
//                int colCount = TableVoli.getColumnCount();
//                for (int i = 0; i < colCount; i++) {
//                    Object value = TableVoli.getValueAt(rowIndex, 0);
//                    System.out.println(value);
//                }

            }
        });*///Fine parentesi mouselistener tablevoli



/*
    public EffettuaPrenotazione(Controller controller, JFrame frameChiamante) {
        this.controller = controller;

        this.frameChiamante = frameChiamante;

        this.voliPrenotabili = new ArrayList<>();
        for (Volo v : controller.getVoli()) {
            if (v.getStato() == StatoVolo.PROGRAMMATO) {
                this.voliPrenotabili.add(v);
            }

        }

        setTitle("Effettua Prenotazione");
        setContentPane(contentPane);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);

        aggiornaTabellaVoli();

        buttonConfermaPrenotazione.addActionListener(e -> {
            int selectedRow = tableVoli.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Seleziona un volo.");
                return;
            }

            String nome = textField1.getText();
            String cognome = textField2.getText();
            String codiceFiscale = textField3.getText();
            String numeroPosto = textField4.getText();

            if (nome.isEmpty() || cognome.isEmpty() || codiceFiscale.isEmpty() || numeroPosto.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi.");
                return;
            }

            VoloPartenzaDaNapoli volo = (VoloPartenzaDaNapoli) voliPrenotabili.get(selectedRow);

            String numeroBiglietto = "TCK-" + UUID.randomUUID().toString().substring(0, 6);

            Prenotazione prenotazione = new Prenotazione(
                    numeroBiglietto,
                    codiceFiscale,
                    nome,
                    cognome,
                    numeroPosto,
                    StatoPrenotazione.CONFERMATA,
                    volo
            );

            controller.aggiungiPrenotazione(prenotazione);

            JOptionPane.showMessageDialog(this, "Prenotazione effettuata con successo.");
            frameChiamante.setVisible(true);
            dispose();
        });

        buttonIndietro.addActionListener(e -> {
            frameChiamante.setVisible(true);
            dispose();
        });
    }

    private void aggiornaTabellaVoli() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> filtrati = new ArrayList<>();

        for (Volo v : controller.getVoli()) {
            if (v instanceof VoloPartenzaDaNapoli && v.getStato() == StatoVolo.PROGRAMMATO) {
                filtrati.add(v);
            }
        }

        modello.settaVoliDaMostrare(filtrati);
        tableVoli.setModel(modello);
    }
*/


    }//parentesi GUI EffettuaPrenotazione

}//parentesi Finale


