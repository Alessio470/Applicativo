package gui;

import model.Volo;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

/**
 * The type Modello tabella voli.

public class ModelloTabellaVoli extends AbstractTableModel {
    private ArrayList<Volo> voliDaMostrare;

    private final String[] intestazioni = {
            "Codice volo", "Compagnia aerea", "Data", "Orario", "Ritardo", "Stato", "Origine", "Destinazione", "Gate"
    };

    @Override
    public String getColumnName(int column) {
        return intestazioni[column];
    }

    @Override
    public int getRowCount() {
        return (voliDaMostrare == null) ? 0 : voliDaMostrare.size();
    }

    @Override
    public int getColumnCount() {
        return intestazioni.length;
    }

    /**
     * Sets voli da mostrare.
     *
     * @param lista the lista

    public void settaVoliDaMostrare(ArrayList<Volo> lista) {
        this.voliDaMostrare = lista;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Volo v = voliDaMostrare.get(rowIndex);

        return switch (columnIndex) {
            case 0 -> v.getCodiceVolo();
            case 1 -> v.getCompagniaAerea();
            case 2 -> v.getDataVolo();
            case 3 -> v.getOrarioPrevisto();
            case 4 -> v.getRitardo();
            case 5 -> v.getStato();
            case 6 -> (v instanceof VoloArrivoANapoli arr) ? arr.getAeroportoOrigine() : "Napoli";
            case 7 -> (v instanceof VoloPartenzaDaNapoli part) ? part.getAeroportoDestinazione() : "Napoli";
            case 8 -> (v instanceof VoloPartenzaDaNapoli part && part.getNumeroGate() != null) ? part.getGate().getNumeroGate() : "-";
            default -> null;
        };
    }

    /**
     * Gets volo at.
     *
     * @param row the row
     * @return the volo at

    public Volo getVoloAt(int row) {
        return voliDaMostrare.get(row);
    }
}

 */

