package gui;

import controller.Controller;
import model.Prenotazione;
import model.Volo;
import model.enums.StatoVolo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Pannello “Area Personale” dell’utente.
 *
 * <p>Mostra l’elenco delle {@link model.Prenotazione prenotazioni} in una Tabella con ordinamento abilitato e celle non editabili.</p>
 *
 * <p>La tabella include una colonna nascosta che contiene l’oggetto {@link model.Volo} associato
 * a ciascuna prenotazione, utile per aprire il dettaglio volo.</p>
 *
 */
public class AreaPersonale {
    private JPanel PanelAeraPersonale;
    private JPanel PanelTitolo;
    private JPanel PanelRicercaRapida;
    private JPanel PanelTabella;
    private JPanel PanelButton;
    private JPanel PanelButtonIndietro;
    private JPanel PanelButtonViewVolo;
    private JPanel PanelButtonCancella;
    private JTable TablePrenotazioni;
    private JButton ButtonIndietro;
    private JButton buttonViewVolo;
    private JButton buttonCancellaPrenotazione;
    private JLabel LabelTitolo;
    private JLabel LabelRicercaRapida;
    private JPanel PanelLabelRicerca;
    private JPanel PanelFieldsRicerca;
    private JPanel PanelRicerca;
    private JTextField textFieldnumerobiglietto;
    private JTextField textFieldnumeroposto;
    private JTextField textFieldnomepasseggero;
    private JButton buttonShow;
    private JComboBox comboBoxStatoPrenotazione;
    private JTextField textFieldcognomepasseggero;
    private JTextField textFieldcodicefiscalepasseggero;
    private JTextField textFieldcodicevolo;
    private JButton ButtonRicerca;

    private JFrame frame;

    private List<Prenotazione> prenotazioni=null;

    /**
     * Costruisce e visualizza l'area personale.
     * <p>Inizializza la {@link JFrame}, configura la {@link JTable} (selezione singola, ordinamento attivo, renderer centrato),
     * popola il modello con le prenotazioni ottenute dal {@link controller.Controller} e nasconde una colonna tecnica
     * che contiene l'oggetto {@link model.Volo} per l'apertura del dettaglio.
     *
     * <p>Listener principali:
     * <br>- Indietro: chiude la finestra corrente e riporta al frame precedente.
     * <br>- Visualizza volo / doppio click riga: recupera il {@link model.Volo} dalla colonna nascosta e apre {@code ViewInfoVolo}.
     *
     * @param prevframe finestra chiamante (per posizionamento/ritorno)
     * @param controller controller applicativo per recuperare le prenotazioni dell'utente
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
        //"codvolo"
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


//Il panel della ricerca all'inizio è disabilitato e metto il bottone col nome giusto
        String VISIBILESI="Show";
        String VISIBILENO="Hide";

        PanelRicerca.setVisible(false);
        buttonShow.setText(VISIBILESI);



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


        buttonCancellaPrenotazione.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                // RIGA SELEZIONATA NELLA VISTA
                int viewRow = TablePrenotazioni.getSelectedRow();

                if (viewRow < 0) {
                    JOptionPane.showMessageDialog(frame,
                            "Seleziona prima una prenotazione dalla tabella.");
                    return;
                }

                // Converte l'indice della vista in indice del modello
                int modelRow = TablePrenotazioni.convertRowIndexToModel(viewRow);

                // Recupera il modello della tabella
                DefaultTableModel modello = (DefaultTableModel) TablePrenotazioni.getModel();

                // Colonna 0 = "numerobiglietto"
                String numeroBiglietto = (String) modello.getValueAt(modelRow, 0);

                boolean ok = controller.cancellaPrenotazioneUtente(numeroBiglietto);

                if (ok) {
                    modello.removeRow(modelRow);
                    JOptionPane.showMessageDialog(frame,
                            "Prenotazione cancellata con successo.");
                }
            }
        });


        buttonShow.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean currvisibility= PanelRicerca.isVisible();
                PanelRicerca.setVisible(!currvisibility);

                if(PanelRicerca.isVisible()){
                    buttonShow.setText(VISIBILENO);
                }else {
                    buttonShow.setText(VISIBILESI);
                }
            }
        });
        ButtonRicerca.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Recupero i dati dai campi di testo
                String numBiglietto = textFieldnumerobiglietto.getText().trim();
                String numPosto = textFieldnumeroposto.getText().trim();
                String codVolo = textFieldcodicevolo.getText().trim();
                String nome = textFieldnomepasseggero.getText().trim();
                String cognome = textFieldcognomepasseggero.getText().trim();
                String cf = textFieldcodicefiscalepasseggero.getText().trim();

                // Trasformo stringhe vuote in null per facilitare il DB (opzionale, dipende dal tuo DAO)
                if (numBiglietto.isEmpty()) numBiglietto = null;
                if (numPosto.isEmpty()) numPosto = null;
                if (codVolo.isEmpty()) codVolo = null;
                if (nome.isEmpty()) nome = null;
                if (cognome.isEmpty()) cognome = null;
                if (cf.isEmpty()) cf = null;

                Prenotazione p = new Prenotazione(numBiglietto,controller.getUsernameUtente(),codVolo,nome, cognome, numPosto, 1, cf);

                // 2. Chiedo al controller i dati filtrati
                List<Prenotazione> risultati = controller.getCercaPrenotazioni(p);

                // 3. Aggiorno la tabella
                //aggiornaTabella(risultati);

            }
        });
    }//Fine parentesi AereaPersonale


}//Fine Parentesi Finale
