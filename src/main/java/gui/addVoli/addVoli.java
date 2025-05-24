package gui.addVoli;

import controller.Controller;
import model.VoloPartenzaDaNapoli;
import model.enums.StatoVolo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class addVoli extends JFrame {
    private JPanel panel1;
    private JPanel codVoloPanel;
    private JPanel nomeCompagniaPanel;
    private JPanel dataVoloPanel;
    private JPanel orarioPrevistoPanel;
    private JPanel statoPanel;
    private JPanel confirmButton;
    private JButton confirm;
    private JRadioButton programmatoRadioButton;
    private JRadioButton decollatoRadioButton;
    private JRadioButton in_ritardoRadioButton;
    private JRadioButton cancellatoRadioButton;
    private JRadioButton atterratoRadioButton;
    private JPanel ritardoPanel;
    private JTextField textField1; // codice volo
    private JTextField textField2; // compagnia aerea
    private JTextField textField3; // data
    private JTextField textField4; // orario
    private JTextField textField5; // ritardo
    private JTextField textField6; // destinazione

    private Controller controller;
    private JFrame frameChiamante;

    public addVoli(Controller controller, JFrame frameChiamante) {
        this.controller = controller;
        this.frameChiamante = frameChiamante;

        setTitle("Inserisci Volo");
        setContentPane(panel1);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Raggruppa i radio button
        ButtonGroup statoGroup = new ButtonGroup();
        statoGroup.add(programmatoRadioButton);
        statoGroup.add(decollatoRadioButton);
        statoGroup.add(in_ritardoRadioButton);
        statoGroup.add(cancellatoRadioButton);
        statoGroup.add(atterratoRadioButton);

        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String codice = textField1.getText();
                String compagnia = textField2.getText();
                String data = textField3.getText();
                String orario = textField4.getText();
                String ritardo = textField5.getText();
                String destinazione = textField6.getText();

                StatoVolo stato = null;
                if (programmatoRadioButton.isSelected()) stato = StatoVolo.PROGRAMMATO;
                else if (decollatoRadioButton.isSelected()) stato = StatoVolo.DECOLLATO;
                else if (in_ritardoRadioButton.isSelected()) stato = StatoVolo.IN_RITARDO;
                else if (cancellatoRadioButton.isSelected()) stato = StatoVolo.CANCELLATO;
                else if (atterratoRadioButton.isSelected()) stato = StatoVolo.ATTERRATO;

                if (stato == null || codice.isEmpty() || compagnia.isEmpty() || data.isEmpty() || orario.isEmpty() || destinazione.isEmpty()) {
                    JOptionPane.showMessageDialog(addVoli.this, "Compila tutti i campi obbligatori.");
                    return;
                }

                VoloPartenzaDaNapoli nuovoVolo = new VoloPartenzaDaNapoli(
                        codice, compagnia, data, orario, ritardo, stato, destinazione
                );

                controller.aggiungiVolo(nuovoVolo);
                JOptionPane.showMessageDialog(addVoli.this, "Volo inserito con successo.");

                dispose();
                frameChiamante.setVisible(true);
                if (frameChiamante instanceof gui.HomepageAmministratore) {
                    ((gui.HomepageAmministratore) frameChiamante).aggiornaTabella();
                }
            }
        });
    }
}
