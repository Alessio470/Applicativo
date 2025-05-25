package gui;

import model.Volo;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ModelloTabellaVoli extends AbstractTableModel {
    private ArrayList<Volo> voliDaMostrare;

    private final String[] intestazioni = {
            "Codice Volo", "AAAAAAAAAAA", "Data", "Orario", "Ritardo", "Stato"
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
            default -> null;
        };
    }

    public Volo getVoloAt(int row) {
        return voliDaMostrare.get(row);
    }
}
