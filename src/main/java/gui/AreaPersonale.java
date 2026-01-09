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
 * Pannello “Area Personale” dell’utente.
 *
 * <p>Mostra l’elenco delle {@link model.Prenotazione prenotazioni} in una Tabella con ordinamento abilitato e celle non editabili.</p>
 *
 * <p>La tabella include una colonna nascosta che contiene l’oggetto {@link model.Volo} associato
 * a ciascuna prenotazione, utile per aprire il dettaglio volo.</p>
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
    private JButton ButtonReset;

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
     * @param prevframe  finestra chiamante (per posizionamento/ritorno)
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

        // CARICAMENTO INIZIALE TABELLA
        List<Prenotazione> prenotazioni = controller.getPrenotazioniUtente();
        aggiornaTabellaPrenotazioni(prenotazioni);

        //Il panel della ricerca all'inizio è disabilitato e metto il bottone col nome giusto
        String VISIBILESI = "Show";
        String VISIBILENO = "Hide";

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
                if (row > -1) {

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

                } else {
                    JOptionPane.showMessageDialog(frame, "Seleziona prima una prenotazione!");
                }
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
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean currvisibility = PanelRicerca.isVisible();
                PanelRicerca.setVisible(!currvisibility);

                if (PanelRicerca.isVisible()) {
                    buttonShow.setText(VISIBILENO);
                } else {
                    buttonShow.setText(VISIBILESI);
                }
            }
        });

        ButtonRicerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Recupero i dati dai campi di testo
                String numBiglietto = textFieldnumerobiglietto.getText().trim();
                String numPosto = textFieldnumeroposto.getText().trim();
                String codVolo = textFieldcodicevolo.getText().trim();
                String nome = textFieldnomepasseggero.getText().trim();
                String cognome = textFieldcognomepasseggero.getText().trim();
                String cf = textFieldcodicefiscalepasseggero.getText().trim();

                // Trasformo stringhe vuote in null
                if (numBiglietto.isEmpty()) numBiglietto = null;
                if (numPosto.isEmpty()) numPosto = null;
                if (codVolo.isEmpty()) codVolo = null;
                if (nome.isEmpty()) nome = null;
                if (cognome.isEmpty()) cognome = null;
                if (cf.isEmpty()) cf = null;

                // 2. Chiedo al controller i dati filtrati
                List<Prenotazione> risultati = controller.getCercaPrenotazioni(
                        new Prenotazione(numBiglietto, controller.getUsernameUtente(), codVolo, nome, cognome, numPosto, 1, cf)
                );

                // 3. Aggiorno la tabella
                aggiornaTabellaPrenotazioni(risultati);
            }
        });

        ButtonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1. Reset campi di testo
                textFieldnumerobiglietto.setText("");
                textFieldnumeroposto.setText("");
                textFieldcodicevolo.setText("");
                textFieldnomepasseggero.setText("");
                textFieldcognomepasseggero.setText("");
                textFieldcodicefiscalepasseggero.setText("");

                // 2. Ricarico tutte le prenotazioni dell’utente
                List<Prenotazione> tutte = controller.getPrenotazioniUtente();

                // 3. Aggiorno la tabella
                aggiornaTabellaPrenotazioni(tutte);
            }
        });
    }
//Fine parentesi AereaPersonale

    /**
     * Aggiorna (o popola) la tabella delle prenotazioni.
     *
     * <p>Ricrea il DefaultTableModel a partire dalla lista passata, mantiene la colonna
     * tecnica con l'oggetto Volo (nascosta), disabilita l'editing delle celle e centra
     * i valori di tutte le colonne visibili.</p>
     *
     * @param lista lista di prenotazioni da visualizzare (se null viene trattata come lista vuota)
     */
    private void aggiornaTabellaPrenotazioni(List<Prenotazione> lista) {

        if (lista == null) {
            lista = java.util.Collections.emptyList();
        }

        String[] colonne = {
                "numerobiglietto",
                "numeroposto",
                "statoprenotazione",
                "codvolo",
                "nomepasseggero",
                "cognomepasseggero",
                "codicefiscalepasseggero",
                "Oggetto" // colonna tecnica (Volo) da nascondere
        };

        DefaultTableModel modello = new DefaultTableModel(colonne, 0);

        for (Prenotazione p : lista) {
            modello.addRow(new Object[]{
                    p.getNumeroBiglietto(),
                    p.getPosto(),
                    p.getStato(),
                    p.getCodiceVolo(),
                    p.getNomePasseggero(),
                    p.getCognomePasseggero(),
                    p.getCodicefiscalepasseggero(),
                    p.getVoloassociato()
            });
        }

        TablePrenotazioni.setModel(modello);

        // Nascondo la colonna "Oggetto" (indice 7)
        int colObj = 7;
        TablePrenotazioni.getColumnModel().getColumn(colObj).setMinWidth(0);
        TablePrenotazioni.getColumnModel().getColumn(colObj).setMaxWidth(0);
        TablePrenotazioni.getColumnModel().getColumn(colObj).setWidth(0);
        TablePrenotazioni.getColumnModel().getColumn(colObj).setPreferredWidth(0);

        // Disabilito editing celle
        TablePrenotazioni.setDefaultEditor(Object.class, null);

        // Centro il contenuto
        javax.swing.table.DefaultTableCellRenderer center = new javax.swing.table.DefaultTableCellRenderer();
        center.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < TablePrenotazioni.getColumnCount(); i++) {
            TablePrenotazioni.getColumnModel().getColumn(i).setCellRenderer(center);
        }

        TablePrenotazioni.setAutoCreateRowSorter(true);
    }




}//Fine Parentesi Finale
