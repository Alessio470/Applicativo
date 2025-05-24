package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JLabel textBenvenuto;
    private JButton buttonExit;
    private JPanel contentPane;
    private JList listPrenotazioni;

    public JFrame frame;

    public Home(JFrame frameChiamante, Controller controller) {
        frame = new JFrame("Home");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(625,270);
       textBenvenuto.setText("Benvenuto " + controller.getUsernameGenerico());

        DefaultListModel<String> model = new DefaultListModel<>();

        for (Prenotazione p : controller.getPrenotazioniUtenteGenerico()) {
            model.addElement(p.toString());  // o qualsiasi info tu voglia mostrare
        }

        listPrenotazioni.setModel(model);

        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameChiamante.setVisible(true);
                frame.setVisible(false);
                frame.dispose();
            }
        });
    }
}
