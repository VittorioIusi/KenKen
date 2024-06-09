package main.grafica.pannelli;

import main.grafica.mediator.Mediator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FinestraSetting extends JFrame implements ActionListener {
    private Mediator mediator;
    private JFrame frame;
    private JTextField dimensioneGrigliaText;
    private JTextField numSolBacktrackingText;
    private JButton okButton;

    public FinestraSetting() {
        inizializza();
    }//costruttore

    private void inizializza() {
        frame = new JFrame("Settings");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        JLabel dimensioneGrigliaLabel = new JLabel("Dimensione Griglia:");
        dimensioneGrigliaLabel.setBounds(10, 20, 150, 25);
        panel.add(dimensioneGrigliaLabel);

        dimensioneGrigliaText = new JTextField(20);
        dimensioneGrigliaText.setBounds(160, 20, 165, 25);
        panel.add(dimensioneGrigliaText);

        JLabel numeroSoluzioniLabel = new JLabel("Numero Soluzioni:");
        numeroSoluzioniLabel.setBounds(10, 60, 150, 25);
        panel.add(numeroSoluzioniLabel);

        numSolBacktrackingText = new JTextField(20);
        numSolBacktrackingText.setBounds(160, 60, 165, 25);
        panel.add(numSolBacktrackingText);

        okButton = new JButton("OK");
        okButton.setBounds(10, 100, 80, 25);
        panel.add(okButton);
        okButton.addActionListener(this);

        frame.setVisible(true);
    }//inizializza

    public void setMediator(Mediator m){
        mediator = m;
    }//setMediator


    public JFrame getFrame() {
        return frame;
    }

    public JTextField getDimensioneGrigliaText() {
        return dimensioneGrigliaText;
    }

    public JTextField getNumSolBacktrackingText() {
        return numSolBacktrackingText;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==okButton){
            if(Integer.parseInt(dimensioneGrigliaText.getText())<3 || Integer.parseInt(dimensioneGrigliaText.getText())>7  || Integer.parseInt(numSolBacktrackingText.getText())<1){
                JOptionPane.showMessageDialog(frame,"parametri non validi");
            }
            else{
                mediator.notify("ConfermaImpostazioni");
                }
        }
    }
}
