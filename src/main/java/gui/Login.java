package gui;

import javax.swing.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Login {
    private JPanel PanelLogin;
    private JPanel PanelTitolo;
    private JPanel PanelCampi;
    private JPanel PanelButtons;
    private JLabel LabelTitolo;
    private JPanel PanelUsername;
    private JLabel LabelUsername;
    private JTextField FieldUsername;
    private JPanel PanelPassword;
    private JLabel LabelPassword;
    private JTextField FieldPassword;
    private JPanel PanelButtonLogin;
    private JButton ButtonLogin;
    private JPanel PanelOppure;
    private JLabel LabelOppure;
    private JPanel PanelButtonRegistrati;
    private JButton ButtonRegistrati;

    public static JFrame frame;

    public static void main(String[] args) {
        frame = new JFrame("HomeCourses");
        frame.setContentPane(new Login().PanelLogin);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(800, 400);
        frame.setVisible(true);
    }



}

