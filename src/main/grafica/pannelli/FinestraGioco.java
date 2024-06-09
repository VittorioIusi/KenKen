package main.grafica.pannelli;


import main.grafica.mediator.ConcreteMediator;
import main.griglia.componenti.Cage;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FinestraGioco extends JFrame implements ActionListener {
    private ConcreteMediator mediator;
    private JFrame frame;
    private JTextField[][] celle;
    private JButton controllaVincoliButton;
    private JButton risolviButton;
    private JButton riprendiButton;
    private JButton prevButton;
    private JButton nextButton;
    private Grid g;

    public FinestraGioco() {
        g = Grid.getInstance();
        inizializza();


    }

    private void inizializza() {
        frame = new JFrame("KenKen - Finestra di Gioco");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Imposta la dimensione in base alla dimensione della griglia
        frame.setLayout(new BorderLayout());

        // Inizializza la griglia delle celle
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(g.getDimension(), g.getDimension()));
        celle = new JTextField[g.getDimension()][g.getDimension()];
        for (int i = 0; i < g.getDimension(); i++) {
            for (int j = 0; j < g.getDimension(); j++) {
                celle[i][j] = new JTextField();
                celle[i][j].setHorizontalAlignment(JTextField.CENTER);
                gridPanel.add(celle[i][j]);
            }
        }

        // Inizializza i pulsanti
        JPanel buttonPanel = new JPanel();
        controllaVincoliButton = new JButton("Controlla Vincoli");
        risolviButton = new JButton("Risolvi");
        riprendiButton = new JButton("Riprendi");
        prevButton = new JButton("Prev");
        nextButton = new JButton("Next");
        buttonPanel.add(controllaVincoliButton);
        buttonPanel.add(risolviButton);
        buttonPanel.add(riprendiButton);
        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);

        // Aggiungi componenti al frame
        frame.add(gridPanel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);


        creaCageGrafici();
        frame.setVisible(true);

    }

    private void creaCageGrafici() {

    }


    public void setMediator(ConcreteMediator mediator) {
        this.mediator = mediator;
    }//setMEdiator

    public JFrame getFrame(){
        return frame;
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }


}//FinestraGioco

