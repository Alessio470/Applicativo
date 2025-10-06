package gui;

import controller.Controller;
import model.Prenotazione;
import model.Volo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * The type Area personale.
 */
public class AreaPersonale {
    private JPanel PanelAeraPersonale;
    private JPanel PanelTitolo;
    private JPanel PanelRicercaRapida;
    private JPanel PanelTabella;
    private JPanel PanelButton;
    private JTable TablePrenotazioni;
    private JButton ButtonIndietro;
    private JLabel LabelTitolo;
    private JLabel LabelRicercaRapida;
    private JComboBox ComboRicerca;
    private JPanel PanelButtonIndietro;
    private JPanel PanelButtonViewVolo;
    private JButton buttonViewVolo;

    private JFrame frame;

    private List<Prenotazione> prenotazioni=null;

    /**
     * Instantiates a new Area personale.
     *
     * @param prevframe  the prevframe
     * @param controller the controller
     */
    public AreaPersonale(JFrame prevframe, Controller controller) {


        frame = new JFrame("Panel Area Personale");
        frame.setTitle("Area Personale"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelAeraPersonale);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 550);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);

        //Tabella in ordine
        TablePrenotazioni.setAutoCreateRowSorter(true);


        // Tabella
        TablePrenotazioni.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TablePrenotazioni.setFillsViewportHeight(true);


//Mettiamo i dati nell array dei dati che andranno nella tabella
        List<Prenotazione> prenotazioni = controller.getPrenotazioniUtente();
        String[] colonnep = {"numerobiglietto", "numeroposto","statoprenotazione","codvolo","nomepasseggero","cognomepasseggero","codicefiscalepasseggero","Oggetto"};

        //String[] colonnev = {"codicevolo","compagniaaerea","datavolo","orarioprevisto","ritardo","statovolo","aeroportoorigine","aeroportodestinazione","numeroGate"};


// Crea il modello dinamico
        DefaultTableModel modello = new DefaultTableModel(colonnep, 0);

// Riempi la tabella con i dati
        for (Prenotazione p : prenotazioni) {
            modello.addRow(new Object[]{
                    p.getNumeroBiglietto(),
                    p.getPosto(),
                    p.getStato(),
                    p.getCodiceVolo(),
                    p.getNomePasseggero(),
                    p.getCognomePasseggero(),
                    p.getCodicefiscalepasseggero(),
                    p.getVoloassociato()  // salvo l’oggetto Volo (colonna nascosta)
            });
        }

// Imposta il modello nella JTable
        TablePrenotazioni.setModel(modello);



        // Nascondi la colonna "Oggetto"
        int colObjIndex = 7; // indice della colonna "Oggetto"
        TablePrenotazioni.getColumnModel().getColumn(colObjIndex).setMinWidth(0);
        TablePrenotazioni.getColumnModel().getColumn(colObjIndex).setMaxWidth(0);
        TablePrenotazioni.getColumnModel().getColumn(colObjIndex).setWidth(0);
        TablePrenotazioni.getColumnModel().getColumn(colObjIndex).setPreferredWidth(0);



// Disabilitiamo modifiche dirette
        TablePrenotazioni.setDefaultEditor(Object.class, null);

// Centriamo tutte le celle
        javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < TablePrenotazioni.getColumnCount(); i++) {
            TablePrenotazioni.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        TablePrenotazioni.setAutoCreateRowSorter(true);



        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (prevframe != null) {
                    prevframe.setVisible(true);
                    prevframe.toFront();
                }
            }
        });//FineParentesi ButtonIndietroListener

        buttonViewVolo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = TablePrenotazioni.getSelectedRow(); // riga selezionata nella vista
                if(row>-1){

                    // converte l'indice della riga della vista in indice del modello
                    int modelRow = TablePrenotazioni.convertRowIndexToModel(row);

                    // Recupera il modello della tabella
                    DefaultTableModel modello = (DefaultTableModel) TablePrenotazioni.getModel();

                    // Recupera l'oggetto Volo dalla colonna nascosta (indice 7)
                    Object obj = modello.getValueAt(modelRow, 7);

                    if (obj instanceof Volo) {
                        Volo voloSelezionato = (Volo) obj;

                        // Apri la finestra ViewInfoVolo passando il volo selezionato
                        new ViewInfoVolo(frame, voloSelezionato);

                        // Nascondi l'area personale
                        frame.setVisible(false);
                    }

                }else JOptionPane.showMessageDialog(frame, "Seleziona prima una prenotazione!");



            }
        });//Fine parentesi ActionListener ButtonViewVolo

        TablePrenotazioni.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) { // doppio click
                    int row = TablePrenotazioni.rowAtPoint(evt.getPoint()); // riga effettivamente cliccata
                    if (row != -1) { // se la riga è valida
                        int modelRow = TablePrenotazioni.convertRowIndexToModel(row);
                        DefaultTableModel modello = (DefaultTableModel) TablePrenotazioni.getModel();
                        Object obj = modello.getValueAt(modelRow, 7); // colonna nascosta "Oggetto"

                        if (obj instanceof Volo) { // controlla che ci sia effettivamente un volo
                            Volo voloSelezionato = (Volo) obj;
                            new ViewInfoVolo(frame, voloSelezionato); // apre finestra modale
                        } else {
                            JOptionPane.showMessageDialog(frame, "Questa prenotazione non ha un volo associato!");
                        }
                    }
                }
            }
        });//Fine parentesi Mouselistener TablePrenotazioni per ViewVolo


    }//Fine parentesi AereaPersonale
}//Fine Parentesi Finale
