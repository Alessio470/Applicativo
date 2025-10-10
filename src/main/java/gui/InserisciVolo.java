package gui;

import controller.Controller;
import model.enums.StatoVolo;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Finestra per l'inserimento di un nuovo volo.
 *
 * <p>Consente di compilare compagnia, aeroporti, data, orario, ritardo, stato e gate, validare i campi e delegare il salvataggio al {@link controller.Controller}.
 *
 * @see controller.Controller
 * @see model.enums.StatoVolo
 */
public class InserisciVolo {

    // ----- Pannelli/label dal .form -----
    private JPanel PanelInserisciVolo;
    private JPanel PanelCampiInserimento;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelRitardo;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JPanel PanelButtons;
    private JPanel PanelButtonConferma;
    private JPanel PanelButtonIndietro;
    private JPanel PanelTitolo;
    private JPanel PanelCompagnia;
    private JPanel PanelStato;
    private JPanel PanelGate;

    private JLabel LabelTitolo;
    private JLabel LabelDataVolo;
    private JLabel LabelOrarioPrevisto;
    private JLabel LabelRitardo;
    private JLabel LabelOrario;
    private JLabel LabelAeroportoOrigine;
    private JLabel LabelAeroportoDestinazione;
    private JLabel LabelCompagnia;
    private JLabel LabelStato;
    private JLabel LabelGate;

    // ----- Campi di input (come da tua form) -----
    private JTextField FieldCompagnia;
    private JTextField FieldAeroportoOrigine;
    private JTextField FieldAeroportoDestinazione;

    private JFormattedTextField formattedTextFieldDataVolo;
    private JFormattedTextField formattedTextFieldOrario;

    private JTextField FieldRitardo;
    private JComboBox<String> ComboStatoVolo;
    private JComboBox<String> ComboGate;

    private JButton ButtonConferma;
    private JButton ButtonIndietro;

    // ----- Finestra -----
    private final JFrame frame;
    private final HomepageAmministratore home;

    /**
     * Costruisce e visualizza la finestra di inserimento volo.
     *
     * <p>Inizializza la finestra, imposta le maschere di input per data e orario,
     * popola le combo di stato e gate, definisce i valori di default e collega i pulsanti:
     *
     * <p>- Conferma: valida i dati e invoca {@link controller.Controller#AddVoliConStato(String, String, String, String, String, String, int, String)}.
     * <br>- Indietro: chiude e torna alla finestra precedente.
     *
     * @param prevFrame finestra chiamante a cui ritornare
     * @param controller controller applicativo per operazioni su voli e lookup di gate
     * @param home riferimento alla home amministratore che potrà essere aggiornata dopo l’inserimento
     */
    public InserisciVolo(JFrame prevFrame, Controller controller, HomepageAmministratore home) {
        frame = new JFrame("Frame Inserisci Volo");
        frame.setTitle("Inserisci Volo");
        frame.setContentPane(PanelInserisciVolo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(850, 520);
        frame.setLocationRelativeTo(prevFrame);
        this.home = home;
        frame.setVisible(true);


        initMasks();
        initStatoCombo();
        initGateCombo(controller);
        initDefaults();

        // Indietro


        ButtonConferma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConferma(controller, prevFrame);
                frame.dispose();
                if(prevFrame != null) {
                    prevFrame.setLocationRelativeTo(null);
                    prevFrame.setVisible(true);
                }
            }
        });

        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if (prevFrame != null) {
                    prevFrame.setVisible(true);
                    prevFrame.toFront();
                }
            }
        });
    }

    // ---------------- Init ----------------

    /**
     * Imposta le maschere di input per data (dd/MM/yyyy) e orario (HH:mm).
     * <br>I campi committano o ripristinano il valore alla perdita di focus.
     */
    private void initMasks() {
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            formattedTextFieldDataVolo.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(dataMask)
            );
            formattedTextFieldDataVolo.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        } catch (Exception ignored) {}

        try {
            MaskFormatter oraMask = new MaskFormatter("##:##");
            oraMask.setPlaceholderCharacter('_');
            formattedTextFieldOrario.setFormatterFactory(
                    new javax.swing.text.DefaultFormatterFactory(oraMask)
            );
            formattedTextFieldOrario.setFocusLostBehavior(JFormattedTextField.COMMIT_OR_REVERT);
        } catch (Exception ignored) {}
    }

    /**
     * Popola la combo degli stati con i valori di {@link StatoVolo} e seleziona di default PROGRAMMATO.
     */
    private void initStatoCombo() {
        ComboStatoVolo.removeAllItems();
        for (StatoVolo s : StatoVolo.values()) {
            ComboStatoVolo.addItem(s.name()); //PROGRAMMATO, DECOLLATO, IN_RITARDO, ATTERRATO, CANCELLATO
        }
        if (ComboStatoVolo.getItemCount() > 0) {
            ComboStatoVolo.setSelectedItem("PROGRAMMATO");
        }
    }

    /**
     * Popola la combo dei gate interrogando il {@link controller.Controller}.
     * <br>In caso di errore mostra un messaggio d’allerta.
     *
     * @param controller controller da cui recuperare l’elenco dei gate
     */
    private void initGateCombo(Controller controller) {
        ComboGate.removeAllItems();
        try {
            List<String> gates = controller.getGates(); //[G1, G2, G3, G4, G5]
            if (gates != null) {
                for (String g : gates) ComboGate.addItem(g);
            }
            if (ComboGate.getItemCount() > 0) ComboGate.setSelectedIndex(0);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Errore nel caricamento dei gate:\n" + ex.getMessage(), "Errore DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Imposta valori iniziali dei campi (es. ritardo = 0).
     */
    private void initDefaults() {
        FieldRitardo.setText("0"); // valore iniziale visibile
        FieldRitardo.setEditable(true);
        FieldRitardo.setEnabled(true);
    }

    // --------------- Azione conferma ---------------

    /**
     * Valida i campi e, se corretti, delega l'inserimento al {@link controller.Controller}.
     * <br>Costruisce i parametri richiesti, interpreta il ritardo come intero (predefinito 0) e mostra un messaggio di esito all’utente.
     *
     * @param controller controller applicativo
     * @param prevFrame finestra chiamante a cui ritornare dopo la chiusura
     */
    private void onConferma(Controller controller, JFrame prevFrame) {
        String compagnia = safe(FieldCompagnia);
        String origine   = safe(FieldAeroportoOrigine);
        String dest      = safe(FieldAeroportoDestinazione);

        String dataStr = formattedTextFieldDataVolo.getText().trim(); // dd/MM/yyyy
        String oraStr  = formattedTextFieldOrario.getText().trim();   // HH:mm

        String gate = ComboGate.getSelectedItem() != null ? ComboGate.getSelectedItem().toString() : null;

        String statoStr = ComboStatoVolo.getSelectedItem() != null ? ComboStatoVolo.getSelectedItem().toString() : "PROGRAMMATO";

        // ritardo (se vuoto o non numerico -> 0)
        int ritardo = 0;
        String rit = FieldRitardo.getText() == null ? "" : FieldRitardo.getText().trim().replace("_", "");
        if (!rit.isEmpty()) {
            try { ritardo = Integer.parseInt(rit); } catch (NumberFormatException ignored) {}
        }

        // Validazioni essenziali
        if (compagnia.isEmpty() || origine.isEmpty() || dest.isEmpty()
                || dataStr.contains("_") || oraStr.contains("_")
                || gate == null || gate.isBlank()) {
            JOptionPane.showMessageDialog(frame, "Compila tutti i campi.\n- Data: dd/MM/yyyy\n- Orario: HH:mm\n- Gate: seleziona un valore", "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (origine.equalsIgnoreCase(dest)) {
            JOptionPane.showMessageDialog(frame, "Origine e destinazione devono essere diverse.", "Dati non validi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            // Inserisce passando anche stato e ritardo
            controller.AddVoliConStato(compagnia, dataStr, oraStr, origine, dest, gate, ritardo, statoStr);
            JOptionPane.showMessageDialog(frame, "Volo inserito con successo.");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Errore durante l'inserimento:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Restituisce il contenuto del campo, trimmato; restituisce stringa vuota se nullo.
     *
     * @param f campo di testo da leggere
     * @return testo trimmato o stringa vuota se mancante
     */
    private String safe(JTextField f) {
        return (f == null || f.getText() == null) ? "" : f.getText().trim();
    }
}
