package gui;

import controller.Controller;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * The type Home utente generico.
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
    private JButton ButtonAereaPersonale;
    private JButton ButtonEffettuaPrenotazione;
    private JTable TableVoli;
    private JButton ButtonLogout;
    private JLabel LabelTitolo;
    private JLabel LabelCiao;
    private Controller controller;


    private static JFrame frame;

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


        ButtonLogout.addActionListener(e -> {
            frame.dispose();
            if (prevframe != null) {
                prevframe.setVisible(true);
                prevframe.toFront();
            }
        });
        ButtonAereaPersonale.addActionListener(e -> {
            AreaPersonale area = new AreaPersonale(frame,controller); // "this" è HomeUtenteGenerico
            frame.setVisible(false);
        });

        ButtonEffettuaPrenotazione.addActionListener(e -> {
            new EffettuaPrenotazione(frame,controller);
            frame.setVisible(false);
        });

        TableVoli.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int rowIndex = TableVoli.rowAtPoint(e.getPoint());
                System.out.println("Riga cliccata: "+rowIndex);
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

