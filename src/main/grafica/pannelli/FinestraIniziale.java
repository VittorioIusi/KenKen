package main.grafica.pannelli;


import main.grafica.mediator.Mediator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FinestraIniziale extends JPanel implements ActionListener {
    private Mediator mediator;
    private JFrame frame;
    private JButton nuovaPartitaButton;
    private JButton caricaPartitaButton;

    public FinestraIniziale() {
        inizializza();
    }//costruttore

    public void setMediator(Mediator m){
        mediator = m;
    }

    private void inizializza() {
        frame = new JFrame("KenKen - Finestra Iniziale");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);  // Centra la finestra sullo schermo
        frame.setResizable(false);  // Impedisce il ridimensionamento della finestra

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);

        panel.setLayout(null);


        nuovaPartitaButton = new JButton("Nuova Partita");
        nuovaPartitaButton.setBounds(100, 115, 200, 30);
        nuovaPartitaButton.addActionListener(this);


        caricaPartitaButton = new JButton("Carica Partita");
        caricaPartitaButton.setBounds(100, 175, 200, 30);
        caricaPartitaButton.addActionListener(this);


        panel.add(nuovaPartitaButton);
        panel.add(caricaPartitaButton);

        frame.setVisible(true);


    }//inizializza

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nuovaPartitaButton) {
            mediator.notify("NuovaPartita");
        }
        else if(e.getSource() == caricaPartitaButton) {
            mediator.notify("CaricaPartita");
        }
    }

    public JFrame getFrame() {
        return frame;
    }//getFrame

    public JButton getNuovaPartitaButton() {
        return nuovaPartitaButton;
    }//getNuovaPartita

    public JButton getCaricaPartitaButton() {
        return caricaPartitaButton;
    }//getCaricaPartita



}//finestraIniziale
