package gui;

import javax.swing.*;

public class Registrazione extends JFrame {
    private JPanel PanelRegistrazione;
    private JPanel PanelTitolo;
    private JPanel PanelCampi;
    private JPanel PanelButtons;
    private JLabel LabelTitolo;
    private JPanel PanelUsername;
    private JPanel PanelPassword;
    private JPanel PanelConfermaPassword;
    private JTextField FieldUsername;
    private JTextField FieldPassword;
    private JTextField FieldConfermaPassword;
    private JLabel LableUsername;
    private JLabel LabelPassword;
    private JLabel LabelConfermaPassword;
    private JPanel PanelButtonRegistrati;
    private JPanel PanelButtonIndietro;
    private JButton ButtonRegistrati;
    private JButton ButtonIndietro;

    public JFrame loginFrame;

    public Registrazione(JFrame loginFrame) {
        this.loginFrame = loginFrame;

        setTitle("Registrazione");
        setContentPane(PanelRegistrazione);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(900, 550);
        setLocationRelativeTo(loginFrame);

        // bottone "Indietro": chiude registrazione e ri-mostra il login
        ButtonIndietro.addActionListener(e -> {
            dispose();
            if (loginFrame != null) {
                loginFrame.setLocationRelativeTo(null);
                loginFrame.setVisible(true);
            }
        });
    }
}
