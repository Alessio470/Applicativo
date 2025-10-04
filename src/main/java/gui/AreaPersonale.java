package gui;

import controller.Controller;

import javax.swing.*;

public class AreaPersonale {
    private JPanel PanelAeraPersonale;
    private JPanel PanelTitolo;
    private JPanel PanelRicercaRapida;
    private JPanel PanelTabella;
    private JPanel PanelButtonIndietro;
    private JTable TableVoli;
    private JButton ButtonIndietro;
    private JLabel LabelTitolo;
    private JLabel LabelRicercaRapida;
    private JComboBox ComboRicerca;

    private JFrame frame;

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
        TableVoli.setAutoCreateRowSorter(true);


//TODO il metodo che gestisce la tabella e tutto

        ButtonIndietro.addActionListener(e -> {
            frame.dispose();
            if (prevframe != null) {
                prevframe.setVisible(true);
                prevframe.toFront();
            }
        });
    }
}
