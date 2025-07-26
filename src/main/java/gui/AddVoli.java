package gui;

import controller.Controller;
import model.enums.StatoVolo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Add voli.
 */
public class AddVoli extends JFrame {
    private JPanel panel1;
    private JPanel codVoloPanel;
    private JPanel nomeCompagniaPanel;
    private JPanel dataVoloPanel;
    private JTextField textField1;
    private JPanel orarioPrebistoPanel;
    private JPanel statoPanel;
    private JPanel confirmButton;
    private JPanel ritardoPanel;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JButton buttonConfirm;
    private JRadioButton programmatoRadioButton;
    private JRadioButton decollatoRadioButton;
    private JRadioButton inRitardoRadioButton;
    private JRadioButton atterratoRadioButton;
    private JRadioButton cancellatoRadioButton;
    private JButton buttonIndietro;

    private Controller controller;
    private JFrame frameChiamante;

    /**
     * Instantiates a new Add voli.
     *
     * @param controller     the controller
     * @param frameChiamante the frame chiamante
     */
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
}
