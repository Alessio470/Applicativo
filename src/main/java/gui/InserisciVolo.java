package gui;

import controller.Controller;

import javax.swing.*;

/**
 * The type Add voli.
*/
public class InserisciVolo extends JFrame {
    private JPanel PanelInserisciVoli;
    private JPanel PanelNomeCompagnia;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelStato;
    private JTextField FieldNomeCompagnia;
    private JTextField FieldDataVolo;
    private JTextField FieldOrario;
    private JButton ButtonConferma;
    private JButton ButtonIndietro;
    private JPanel PanelCampiInserimento;
    private JLabel LabelNomeCompagnia;
    private JLabel LabelDataVolo;
    private JLabel LabelOrario;
    private JTextField FieldAeroportoOrigine;
    private JTextField FieldAeroportoDestinazione;
    private JComboBox ComboStatiVolo;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JLabel LabelAeroportoOrigine;
    private JLabel LabelAeroportoDestinazione;
    private JPanel PanelButtons;
    private JPanel PanelButtonConferma;
    private JPanel PanelButtonIndietro;
    private JLabel LabelStato;

    private Controller controller;
    private JFrame frameChiamante;

    /**
     * Instantiates a new Add voli.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
*/
    /*
    public AddVoli(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        setTitle("Inserisci volo");
        setContentPane(panel1);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ButtonGroup statoGroup = new ButtonGroup();
        statoGroup.add(programmatoRadioButton);
        statoGroup.add(decollatoRadioButton);
        statoGroup.add(inRitardoRadioButton);
        statoGroup.add(atterratoRadioButton);
        statoGroup.add(cancellatoRadioButton);

        buttonConfirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = textField1.getText();
                String compagnia = textField2.getText();
                String data = textField3.getText();
                String orario = textField4.getText();
                String ritardo = textField5.getText();

                StatoVolo stato = null;
                if (programmatoRadioButton.isSelected()) stato = StatoVolo.PROGRAMMATO;
                else if (decollatoRadioButton.isSelected()) stato = StatoVolo.DECOLLATO;
                else if (inRitardoRadioButton.isSelected()) stato = StatoVolo.IN_RITARDO;
                else if (cancellatoRadioButton.isSelected()) stato = StatoVolo.CANCELLATO;
                else if (atterratoRadioButton.isSelected()) stato = StatoVolo.ATTERRATO;

                if (stato == null || codice.isEmpty() || compagnia.isEmpty() || data.isEmpty() || orario.isEmpty()) {
                    JOptionPane.showMessageDialog(AddVoli.this, "Compila tutti i campi obbligatori.");
                    return;
                }

                VoloPartenzaDaNapoli nuovoVolo = new VoloPartenzaDaNapoli(
                        codice, compagnia, data, orario, ritardo, stato, "DESTINAZIONE_MANCANTE"
                );

                controller.aggiungiVolo(nuovoVolo);
                JOptionPane.showMessageDialog(AddVoli.this, "Volo inserito con successo.");
                frameChiamante.setVisible(true);
                dispose();
                if (frameChiamante instanceof HomepageAmministratore) {
                    ((HomepageAmministratore) frameChiamante).aggiornaTabella();
                }
            }
        });

        buttonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                dispose();
            }
        });
    }

     */
}



