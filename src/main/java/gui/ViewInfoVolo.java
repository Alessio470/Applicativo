package gui;

import model.Volo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type View info volo.
 */
public class ViewInfoVolo {
    private JPanel PanelViewInfoVolo;
    private JPanel PanelCampiInserimento;
    private JPanel PanelDataVolo;
    private JLabel LabelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JLabel LabelOrario;
    private JPanel PanelRitardo;
    private JLabel LabelRitardo;
    private JPanel PanelAeroportoOrigine;
    private JLabel LabelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JLabel LabelAeroportoDestinazione;
    private JPanel PanelCompagnia;
    private JLabel LabelCompagnia;
    private JPanel PanelStato;
    private JLabel LabelStato;
    private JPanel PanelGate;
    private JLabel LabelGate;
    private JPanel PanelButtons;
    private JPanel PanelButtonIndietro;
    private JButton ButtonIndietro;
    private JPanel PanelTitolo;
    private JLabel LabelTitolo;

    //Tutte le label Get Sono qui
    private JLabel labelGetCompagnia;
    private JLabel labelGetStato;
    private JLabel labelGetGate;
    private JLabel labelGetAeroportoDestinazione;
    private JLabel labelGetAeroportoOrigine;
    private JLabel labelGetRitardo;
    private JLabel labelGetOrarioPrevisto;
    private JLabel labelGetDataVolo;
    private JLabel labelGetCodiceVolo;

    private JFrame frame;

    /**
     * Instantiates a new View info volo.
     *
     * @param prevframe the prevframe
     * @param voloview  the voloview
     */
    public ViewInfoVolo(JFrame prevframe, Volo voloview) {

        frame = new JFrame("Panel ViewInfoVolo");
        frame.setTitle("View InfoVolo"); //QUA HO FATTO LE ROBE PER INIZIALIZZARE LA FRAME
        frame.setContentPane(PanelViewInfoVolo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 450);
        frame.setLocationRelativeTo(prevframe);
        frame.setVisible(true);

        frame.toFront(); //Per mettere questa gui sempre sopra

         labelGetCompagnia.setText(voloview.getCompagnia());
         labelGetStato.setText(voloview.getStato().toString());
         labelGetGate.setText(voloview.getGate());
         labelGetAeroportoDestinazione.setText(voloview.getAeroportoDestinazione());
         labelGetAeroportoOrigine.setText(voloview.getAeroportoOrigine());
         labelGetRitardo.setText(Integer.toString(voloview.getRitardoMinuti())+" Min");
         labelGetOrarioPrevisto.setText(voloview.getOrarioStr());
         labelGetDataVolo.setText(voloview.getDataStr());
         labelGetCodiceVolo.setText(voloview.getCodiceV());

        ButtonIndietro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                if(prevframe!=null){
                    prevframe.setVisible(true);
                    prevframe.toFront();
                }
            }
        });//Fine parentesi ActionListener ButtonIndietro


    }

}//Fine parentesi Finale
