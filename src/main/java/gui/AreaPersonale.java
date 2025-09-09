package gui;

import javax.swing.*;

public class AreaPersonale extends JFrame {
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

    private final JFrame homeFrame;

    public AreaPersonale(JFrame homeFrame) {
        this.homeFrame = homeFrame;

        setTitle("Area Personale");
        setContentPane(PanelAeraPersonale);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(800, 600);
        setLocationRelativeTo(homeFrame);

        ButtonIndietro.addActionListener(e -> {
            dispose();
            if (homeFrame != null) {
                homeFrame.setVisible(true);
                homeFrame.toFront();
            }
        });
    }
}
