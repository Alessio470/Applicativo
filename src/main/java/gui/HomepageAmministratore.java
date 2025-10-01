package gui;

import controller.Controller;
import database.ConnessioneDatabase;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


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

    private Controller controller;

    private static JFrame frame;
    public JFrame prevframe;

    private static final DateTimeFormatter DF = DateTimeFormatter.ofPattern("dd/MM/uuuu");
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm");

    public HomepageAmministratore(JFrame prevframe, Controller controller) {
        this.prevframe = prevframe;
        this.controller = controller;

        // Inizializza frame
        frame = new JFrame("Home Utente Amministratore");
        frame.setContentPane(PanelHomepageAmministratore);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 600);
        frame.setLocationRelativeTo(prevframe);

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

        // Stato volo: etichette leggibili (mappate a int in UPDATE)
        if (ComboStatoVolo.getItemCount() == 0) {
            ComboStatoVolo.addItem("programmato");
            ComboStatoVolo.addItem("decollato");
            ComboStatoVolo.addItem("in_ritardo");
            ComboStatoVolo.addItem("atterrato");
            ComboStatoVolo.addItem("cancellato");
        }

        // Tabella
        TabellaVoli.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        TabellaVoli.setFillsViewportHeight(true);

        // Carica dati e collega selezione
        caricaVoliDaPerNapoli();
        collegaSelezioneRiga();

        // Pulsanti
        ButtonLogout.addActionListener(e -> {
            frame.dispose();
            if (prevframe != null) {
                prevframe.setVisible(true);
                prevframe.toFront();
            }
        });

        ButtonInserisciVolo.addActionListener(e -> {
            InserisciVolo inserisci = new InserisciVolo(frame, controller);
            frame.setVisible(false);
        });

        ButtonConfermaModifica.addActionListener(e -> confermaModifica());

        frame.setVisible(true);
    }

    /**
     * Popola la JTable con i voli che hanno Napoli (Capodichino) come origine o destinazione.
     * Usa ILIKE perché nel DB i nomi sono frasi tipo "Aeroporto di Napoli Capodichino".
     */
    private void caricaVoliDaPerNapoli() {
        String[] colonne = {
                "Codice volo", "Compagnia", "Origine", "Destinazione",
                "Data", "Orario", "Stato", "Ritardo (min)", "Gate"
        };
        DefaultTableModel model = new DefaultTableModel(colonne, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        final String sql =
                "SELECT codicevolo, compagniaaerea, aeroportoorigine, aeroportodestinazione, " +
                        "       datavolo, orarioprevisto, statovolo, ritardo, \"numeroGate\" " +
                        "FROM volo " +
                        "WHERE aeroportoorigine ILIKE '%Napoli%' " +
                        "   OR aeroportodestinazione ILIKE '%Napoli%' " +
                        "ORDER BY datavolo, orarioprevisto, codicevolo";

        try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String cod    = rs.getString("codicevolo");
                String comp   = rs.getString("compagniaaerea");
                String orig   = rs.getString("aeroportoorigine");
                String dest   = rs.getString("aeroportodestinazione");

                Date data     = rs.getDate("datavolo");
                Time orario   = rs.getTime("orarioprevisto");
                String dataS  = (data != null)   ? data.toLocalDate().format(DF) : "";
                String oraS   = (orario != null) ? orario.toLocalTime().format(TF) : "";

                // statovolo è INTEGER -> mappo a etichetta leggibile
                int statoI = rs.getInt("statovolo");
                String statoS = rs.wasNull() ? "" : switch (statoI) {
                    case 1 -> "programmato";
                    case 2 -> "decollato";
                    case 3 -> "in_ritardo";
                    case 4 -> "atterrato";
                    case 5 -> "cancellato";
                    default -> "sconosciuto(" + statoI + ")";
                };

                Integer ritardo = (Integer) rs.getObject("ritardo");
                String gate     = rs.getString("numeroGate");

                model.addRow(new Object[]{cod, comp, orig, dest, dataS, oraS, statoS, ritardo, gate});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            model.addRow(new Object[]{"ERRORE DB: " + ex.getMessage(), "", "", "", "", "", "", "", ""});
        }

        TabellaVoli.setModel(model);
        TabellaVoli.getColumnModel().getColumn(0).setPreferredWidth(110);
        TabellaVoli.getColumnModel().getColumn(1).setPreferredWidth(150);
        TabellaVoli.getColumnModel().getColumn(2).setPreferredWidth(220);
        TabellaVoli.getColumnModel().getColumn(3).setPreferredWidth(240);
        TabellaVoli.getColumnModel().getColumn(4).setPreferredWidth(90);
        TabellaVoli.getColumnModel().getColumn(5).setPreferredWidth(80);
        TabellaVoli.getColumnModel().getColumn(6).setPreferredWidth(110);
        TabellaVoli.getColumnModel().getColumn(7).setPreferredWidth(110);
        TabellaVoli.getColumnModel().getColumn(8).setPreferredWidth(70);
        TabellaVoli.setDefaultEditor(Object.class, null);
    }

    /**
     * Quando selezioni una riga, popola i campi in basso.
     */
    private void collegaSelezioneRiga() {
        TabellaVoli.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if (e.getValueIsAdjusting()) return;
            int r = TabellaVoli.getSelectedRow();
            if (r < 0) return;

            FieldCompagniaAerea.setText(valueAt(r, 1));
            FieldAeroportoOrigine.setText(valueAt(r, 2));
            FieldAeroportoDestinazione.setText(valueAt(r, 3));
            formattedTextFieldData.setText(valueAt(r, 4));     // dd/MM/yyyy
            formattedTextFieldOrario.setText(valueAt(r, 5));   // HH:mm
            ComboStatoVolo.setSelectedItem(valueAt(r, 6));
        });
    }

    private String valueAt(int row, int col) {
        Object v = TabellaVoli.getValueAt(row, col);
        return v == null ? "" : v.toString();
    }

    /**
     * UPDATE della riga selezionata con i valori dei campi.
     * Aggiorna: compagnia, origine, destinazione, data, orario, statovolo (INT).
     */
    private void confermaModifica() {
        int r = TabellaVoli.getSelectedRow();
        if (r < 0) {
            JOptionPane.showMessageDialog(frame, "Seleziona prima una riga della tabella.",
                    "Nessuna riga selezionata", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String codiceVolo = valueAt(r, 0);

        String compagnia  = FieldCompagniaAerea.getText().trim();
        String origine    = FieldAeroportoOrigine.getText().trim();
        String destinaz   = FieldAeroportoDestinazione.getText().trim();

        String dataStr    = formattedTextFieldData.getText().trim();   // dd/MM/yyyy
        String oraStr     = formattedTextFieldOrario.getText().trim(); // HH:mm
        String statoLbl   = (String) ComboStatoVolo.getSelectedItem();

        if (compagnia.isEmpty() || origine.isEmpty() || destinaz.isEmpty()
                || dataStr.contains("_") || oraStr.contains("_")) {
            JOptionPane.showMessageDialog(frame,
                    "Compila tutti i campi (data dd/MM/yyyy, orario HH:mm).",
                    "Campi mancanti", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalDate d = LocalDate.parse(dataStr, DF);
            LocalTime t = LocalTime.parse(oraStr, TF);

            // Mappa etichetta -> int per DB
            int s = switch (statoLbl) {
                case "programmato" -> 1;
                case "decollato"   -> 2;
                case "in_ritardo"  -> 3;
                case "atterrato"   -> 4;
                case "cancellato"  -> 5;
                default -> 1;
            };

            final String sql =
                    "UPDATE volo " +
                            "SET compagniaaerea=?, aeroportoorigine=?, aeroportodestinazione=?, " +
                            "    datavolo=?, orarioprevisto=?, statovolo=? " +
                            "WHERE codicevolo=?";

            try (Connection cn = ConnessioneDatabase.getInstance().getConnection();
                 PreparedStatement ps = cn.prepareStatement(sql)) {

                ps.setString(1, compagnia);
                ps.setString(2, origine);
                ps.setString(3, destinaz);
                ps.setDate(4, Date.valueOf(d));
                ps.setTime(5, Time.valueOf(t));
                ps.setInt(6, s);
                ps.setString(7, codiceVolo);

                int n = ps.executeUpdate();
                if (n == 1) {
                    JOptionPane.showMessageDialog(frame, "Volo aggiornato correttamente.");
                    caricaVoliDaPerNapoli();        // refresh tabella
                    riselezionaPerCodice(codiceVolo);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "Nessuna riga aggiornata (codice non trovato?).",
                            "Aggiornamento", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame,
                    "Errore durante l'aggiornamento:\n" + ex.getMessage(),
                    "Errore DB", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void riselezionaPerCodice(String codice) {
        for (int i = 0; i < TabellaVoli.getRowCount(); i++) {
            if (codice.equals(String.valueOf(TabellaVoli.getValueAt(i, 0)))) {
                TabellaVoli.setRowSelectionInterval(i, i);
                TabellaVoli.scrollRectToVisible(TabellaVoli.getCellRect(i, 0, true));
                break;
            }
        }
    }
}
