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
 * Home dell'utente generico.
 *
 * <p>Mostra la lista dei voli, consente l’accesso all’area personale e l’avvio del flusso di prenotazione.
 *
 * @see controller.Controller
 * @see gui.AreaPersonale
 * @see gui.EffettuaPrenotazione
 * @see model.Volo
 */
public class HomeUtenteGenerico {
    private JPanel contentPane;
    private JPanel PanelHomeGenerico;
    private JPanel PanelTitolo;
    private JPanel PanelButton;
    private JPanel PanelTabellaVoli;
    private JPanel PanelButtonLogout;
    private JPanel PanelButtonAreaPersonale;
    private JPanel PanelButtonEffettuaPrenotazione;
    private JTable TableVoli;
    private JButton ButtonAreaPersonale;
    private JButton ButtonEffettuaPrenotazione;
    private JButton ButtonLogout;
    private JLabel LabelTitolo;
    private JLabel LabelCiao;
    private JPanel PanelCerca;
    private JTextField codiceVoloTextField;
    private JTextField compagniaTextField;
    private JTextField aeroportoOrigineTextField;
    private JTextField aeroportoDestinazioneTextField;
    private JTextField dataTextField;
    private JTextField orarioTextField;
    private JTextField gateTextField;
    private JButton cercaButton;


    private static JFrame frame;
    private Controller controller;

    /**
     * Costruisce e visualizza la home utente generico.
     *
     * <p>Inizializza la finestra, saluta l’utente loggato, carica i voli dal {@link controller.Controller},
     * popola la tabella (ordinamento attivo, celle non editabili, renderer centrato) e collega i pulsanti:
     *
     * <p>- Logout: chiude la finestra, effettua il logout e ritorna al frame precedente.
     * <br>- Area personale: apre {@link gui.AreaPersonale} e nasconde la home.
     * <br>- Effettua prenotazione: apre {@link gui.EffettuaPrenotazione} e nasconde la home.
     *
     * @param prevframe finestra chiamante a cui ritornare
     * @param controller controller applicativo per ottenere i voli e lo username
     */
    public HomeUtenteGenerico(JFrame prevframe, Controller controller) {


        frame = new JFrame("Panel Utente Generico");
        frame.setTitle("Home Utente Generico"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelHomeGenerico);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);


        LabelCiao.setText("Ciao! "+controller.getUsernameUtente());
        // Intestazioni colonne
        String[] colonne = {"Codice Volo", "Compagnia", "Origine", "Destinazione", "Data", "Orario", "Ritardo", "Stato", "Gate"};

// Dati fittizi

      // Object[][] dati = {
  //  {"AZ123", "Alitalia", "Napoli", "Roma", "27/09/2025", "14:30", 0, "PROGRAMMATO", "A1"},
   // {"FR456", "Ryanair", "Napoli", "Milano", "27/09/2025", "16:00", 15, "IN_RITARDO", "B2"},
//{"DL789", "Delta", "Napoli", "Parigi", "28/09/2025", "09:45", 0, "PROGRAMMATO", "C3"}
//};
        //Mettiamo i dati nell array dei dati che andranno nella tabella
        List<Volo> voli = controller.getVoli();

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

        //tabella in ordine
        TableVoli.setAutoCreateRowSorter(true);


// Disabilitiamo modifiche dirette
        TableVoli.setDefaultEditor(Object.class, null);

// Centriamo tutte le celle

        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < TableVoli.getColumnCount(); i++) {
            TableVoli.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }


        ButtonLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                frame.dispose();
                controller.doLogoutUser();
                if (prevframe != null) {
                    prevframe.setVisible(true);
                    prevframe.toFront();
                }
            }
        });

        ButtonAreaPersonale.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                AreaPersonale area = new AreaPersonale(frame,controller); // "this" è HomeUtenteGenerico
                frame.setVisible(false);
            }
        });


        ButtonEffettuaPrenotazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent e){
                new EffettuaPrenotazione(frame,controller);
                frame.setVisible(false);
            }
        });


        TableVoli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int rowIndex = TableVoli.rowAtPoint(e.getPoint());
                //System.out.println("Riga cliccata: "+rowIndex);
            }
        });
        cercaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Volo> voli = controller.cercaVoli(
                        codiceVoloTextField.getText().trim(),
                        compagniaTextField.getText().trim(),
                        aeroportoOrigineTextField.getText().trim(),
                        aeroportoDestinazioneTextField.getText().trim(),
                        dataTextField.getText().trim(),
                        orarioTextField.getText().trim(),
                        gateTextField.getText().trim()
                );
                Object[][] dati = new Object[voli.size()][9]; // 9 colonne come intestazioni
                for (int i = 0; i < voli.size(); i++) {
                    Volo volo = voli.get(i);
                    dati[i][0] = volo.getCodiceV();
                    dati[i][1] = volo.getCompagnia();
                    dati[i][2] = volo.getAeroportoOrigine();
                    dati[i][3] = volo.getAeroportoDestinazione();
                    dati[i][4] = volo.getDataStr();
                    dati[i][5] = volo.getOrarioStr();
                    dati[i][6] = volo.getRitardoMinuti();
                    dati[i][7] = volo.getStato().name();
                    dati[i][8] = volo.getGate();
                }
                String[] colonne = {"Codice Volo", "Compagnia", "Origine", "Destinazione", "Data", "Orario", "Ritardo", "Stato", "Gate"};
                javax.swing.table.DefaultTableModel modello = new javax.swing.table.DefaultTableModel(dati, colonne);
                TableVoli.setModel(modello);

                // Reapply center renderer
                javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                for (int i = 0; i < TableVoli.getColumnCount(); i++) {
                    TableVoli.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                }
            }
        });
    }

/*
    public HomeUtenteGenerico(JFrame frameChiamante, Controller controller) {
        this.controller = controller;
        this.frame = new JFrame("Home");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(625, 270);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        textBenvenuto.setText("Benvenuto " + controller.getUsernameGenerico());

        aggiornaTabella();

        buttonExit.addActionListener(e -> {
            frameChiamante.setVisible(true);
            frame.dispose();
        });

        buttonVisualizzaVoliPrenotati.addActionListener(e -> {
            JFrame prenotazioniFrame = new PrenotazioniUtente(controller, frame);
            prenotazioniFrame.setVisible(true);
            frame.setVisible(false);
        });

        buttonEffettuaPrenotazione.addActionListener(e -> {
            JFrame finestraPrenota = new EffettuaPrenotazione(controller, frame);
            finestraPrenota.setVisible(true);
            frame.setVisible(false);
        });
    }

    /**
     * Aggiorna tabella.
     */

/*
    public void aggiornaTabella() {
        ModelloTabellaVoli modello = new ModelloTabellaVoli();
        ArrayList<Volo> listaVoli = controller.getVoli();
        modello.settaVoliDaMostrare(listaVoli);
        tableVoliGenerali.setModel(modello);
    }
 */
}

