package gui;

import model.Volo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Finestra di dettaglio di un {@link model.Volo}.
 *
 * <p>Mostra i campi principali del volo (codice, compagnia, aeroporti, data, orario,
 * ritardo, stato e gate) in sola lettura, con pulsante per tornare alla finestra precedente.
 *
 * @see model.Volo
 */
public class ViewInfoVolo {
    private JPanel PanelViewInfoVolo;
    private JPanel PanelCampiInserimento;
    private JPanel PanelDataVolo;
    private JPanel PanelOrarioPrevisto;
    private JPanel PanelRitardo;
    private JPanel PanelAeroportoOrigine;
    private JPanel PanelAeroportoDestinazione;
    private JPanel PanelCompagnia;
    private JPanel PanelStato;
    private JPanel PanelGate;
    private JPanel PanelButtons;
    private JPanel PanelButtonIndietro;
    private JPanel PanelTitolo;
    private JButton ButtonIndietro;
    private JLabel LabelDataVolo;
    private JLabel LabelOrario;
    private JLabel LabelRitardo;
    private JLabel LabelAeroportoOrigine;
    private JLabel LabelAeroportoDestinazione;
    private JLabel LabelCompagnia;
    private JLabel LabelStato;
    private JLabel LabelGate;
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
     * Costruisce e visualizza la finestra di dettaglio volo.
     *
     * <p>Inizializza la finestra, porta a fronte la GUI e valorizza le label con
     * i dati del {@link model.Volo} passato.
     *
     * <p>Pulsante:
     * <br>- Indietro: chiude la finestra e riporta al frame precedente.
     *
     * @param prevframe finestra chiamante a cui ritornare
     * @param voloview  volo di cui mostrare i dettagli
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
