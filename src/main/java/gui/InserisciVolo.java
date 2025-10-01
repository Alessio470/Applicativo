package gui;

import controller.Controller;
import database.ConnessioneDatabase;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Finestra "Inserisci Volo": inserisce una riga nella tabella 'volo'.
 * Tabella volo:
 *  codicevolo TEXT PK, compagniaaerea TEXT, datavolo DATE, orarioprevisto TIME,
 *  ritardo INTEGER, statovolo INTEGER, aeroportoorigine TEXT, aeroportodestinazione TEXT, "numeroGate" TEXT
 */
public class InserisciVolo {

    // --- Componenti del form (rispetta i nomi che vedi nello screenshot) ---
    // --- Pannelli BINDATI come nel .form ---
    private JPanel PanelInserisciVolo;
    private JPanel PanelCampiInserimento;

    // (non sono necessari a livello logico, ma li dichiaro per soddisfare le binding del .form se presenti)
    private JPanel PanelNomeCompagnia;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelStato;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JPanel PanelButtons;
    private JPanel PanelButtonConferma;
    private JPanel PanelButtonIndietro;
    private JPanel PanelTitolo;

    // --- Componenti di input (devono coincidere con le Binding del .form) ---
    private JLabel LabelTitolo;
    private JLabel LabelNomeCompagnia;
    private JLabel LabelDataVolo;
    private JLabel LabelOrarioPrevisto;
    private JLabel LabelNGate;
    private JLabel LabelOrario;
    private JLabel LabelAeroportoOrigine;
    private JLabel LabelAeroportoDestinazione;
    private JComboBox ComboStatiVolo;
    private JFormattedTextField FormattedFieldOrario;
    private JFormattedTextField formattedFieldDataVolo;


    private JComboBox<String> ComboCompagniaAerea;   // editabile
    private JTextField FieldAeroportoOrigine;
    private JTextField FieldAeroportoDestinazione;
    private JFormattedTextField FieldData;           // dd/MM/yyyy
    private JFormattedTextField FieldOrario;         // HH:mm
    private JComboBox<String> ComboNumeroGate;       // valori da tabella gate
    private JButton ButtonConferma;
    private JButton ButtonIndietro;

    // --- Supporto finestra/contesto ---
    private final Controller controller;
    private final JFrame prevFrame;   // HomepageAmministratore che ha aperto questa finestra
    private final JFrame frame;

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");

    public InserisciVolo(JFrame prevFrame, Controller controller) {
        this.prevFrame  = prevFrame;
        this.controller = controller;

        // usa il ROOT panel bindato nel .form
        frame = new JFrame("Inserisci Volo");
        frame.setContentPane(PanelInserisciVolo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(850, 520);
        frame.setLocationRelativeTo(prevFrame);

        initMasks();
        initCombos();

        // Pulsanti
        ButtonIndietro.addActionListener(e -> {
            frame.dispose();
            if (prevFrame != null) {
                prevFrame.setVisible(true);
                prevFrame.toFront();
            }
        });

        ButtonConferma.addActionListener(e -> onConferma());

        frame.setVisible(true);
    }

    // --- Inizializzazioni ---

    private void initMasks() {
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter('_');
            FieldData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(dataMask));
        } catch (Exception ignored) {}

        try {
            MaskFormatter oraMask = new MaskFormatter("##:##");
            oraMask.setPlaceholderCharacter('_');
            FieldOrario.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(oraMask));
        } catch (Exception ignored) {}
    }

    private void initCombos() {
        // Compagnia: prendo distinct e rendo la combo editabile
        ComboCompagniaAerea.setEditable(true);
        try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT DISTINCT compagniaaerea FROM volo ORDER BY 1")) {
            while (rs.next()) ComboCompagniaAerea.addItem(rs.getString(1));
        } catch (SQLException ex) {
            System.err.println("Warn: non riesco a caricare le compagnie: " + ex.getMessage());
        }

        // Gate: dalla tabella gate ("numeroGate")
        ComboNumeroGate.removeAllItems();
        try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
             Statement st = cn.createStatement();
             ResultSet rs = st.executeQuery("SELECT \"numeroGate\" FROM gate ORDER BY 1")) {
            while (rs.next()) ComboNumeroGate.addItem(rs.getString(1));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame,
                    "Errore nel caricamento dei gate:\n" + ex.getMessage(),
                    "Errore DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- Azioni ---

    private void onConferma() {
        String compagnia = getComboText(ComboCompagniaAerea).trim();
        String origine   = FieldAeroportoOrigine.getText().trim();
        String destinaz  = FieldAeroportoDestinazione.getText().trim();
        String dataStr   = FieldData.getText().trim();
        String oraStr    = FieldOrario.getText().trim();
        String gate      = (String) ComboNumeroGate.getSelectedItem();

        if (compagnia.isEmpty() || origine.isEmpty() || destinaz.isEmpty()
                || dataStr.contains("_") || oraStr.contains("_") || gate == null || gate.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "Compila tutti i campi.\n- Data dd/MM/yyyy\n- Orario HH:mm",
                    "Campi mancanti", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (origine.equalsIgnoreCase(destinaz)) {
            JOptionPane.showMessageDialog(frame,
                    "Origine e destinazione devono essere diverse.",
                    "Dati non validi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            LocalDate data  = LocalDate.parse(dataStr, DF);
            LocalTime ora   = LocalTime.parse(oraStr, TF);

            String codiceVolo = generaCodiceVolo(compagnia);

            final String sql =
                    "INSERT INTO volo (codicevolo, compagniaaerea, datavolo, orarioprevisto, " +
                            "                   ritardo, statovolo, aeroportoorigine, aeroportodestinazione, \"numeroGate\") " +
                            "VALUES (?, ?, ?, ?, 0, 1, ?, ?, ?)";

            try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setString(1, codiceVolo);
                ps.setString(2, compagnia);
                ps.setDate(3, Date.valueOf(data));
                ps.setTime(4, Time.valueOf(ora));
                ps.setString(5, origine);
                ps.setString(6, destinaz);
                ps.setString(7, gate);

                ps.executeUpdate();
            }

            JOptionPane.showMessageDialog(frame,
                    "Volo inserito con successo.\nCodice: " + codiceVolo,
                    "OK", JOptionPane.INFORMATION_MESSAGE);

            if (prevFrame != null) {
                prevFrame.setVisible(true);
                prevFrame.toFront();
            }
            frame.dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Errore durante l'inserimento:\n" + ex.getMessage(),
                    "Errore DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static String getComboText(JComboBox<String> combo) {
        Object ed = combo.isEditable() ? combo.getEditor().getItem() : combo.getSelectedItem();
        return ed == null ? "" : ed.toString();
    }

    /** Genera codice tipo IA101, IA102... in base alla compagnia. */
    private String generaCodiceVolo(String compagnia) throws SQLException {
        String pref = compagnia.replaceAll("[^A-Za-z]", "").toUpperCase();
        pref = (pref.length() >= 2) ? pref.substring(0, 2) : (pref + "X").substring(0, 2);

        int next = 1;
        final String sqlCount = "SELECT COUNT(*) FROM volo WHERE compagniaaerea = ?";
        try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sqlCount)) {
            ps.setString(1, compagnia);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) next = rs.getInt(1) + 1;
            }
        }

        String candidate;
        while (true) {
            candidate = pref + String.format("%03d", next);
            if (!esisteCodice(candidate)) break;
            next++;
        }
        return candidate;
    }

    private boolean esisteCodice(String codice) throws SQLException {
        final String sql = "SELECT 1 FROM volo WHERE codicevolo = ? LIMIT 1";
        try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, codice);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }
}
