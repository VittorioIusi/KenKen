package main.grafica.pannelli;


import main.grafica.mediator.Mediator;
import main.griglia.Memento;
import main.griglia.componenti.Cage;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class FinestraGioco extends JFrame implements ActionListener {
    private Mediator mediator;
    private JFrame frame;
    private JTextField[][] celle;
    private  JLabel[][] labelTargets;
    private JButton controllaVincoliButton;

    private JButton risolviButton;
    private JButton riprendiButton;
    private JButton prevButton;
    private JButton nextButton;

    private JMenuItem salvaPartitaMenuItem;


    private Grid g;
    private Memento memento;

    public FinestraGioco() {
        g = Grid.getInstance();
        inizializza();


    }

    private void inizializza() {
        frame = new JFrame("KenKen - Finestra di Gioco");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600); // Imposta la dimensione in base alla dimensione della griglia
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);  // Centra la finestra sullo schermo


        // Aggiungi la barra dei menu
        JMenuBar menuBar = new JMenuBar();

        // Menu "File"
        JMenu menuFile = new JMenu("File");
        salvaPartitaMenuItem = new JMenuItem("Salva Partita");
        salvaPartitaMenuItem.addActionListener(this);
        menuFile.add(salvaPartitaMenuItem);
        menuBar.add(menuFile);

        // Menu "Help"
        JMenu menuHelp = new JMenu("Help");
        JMenuItem regoleMenuItem = new JMenuItem("Regole");
        regoleMenuItem.addActionListener(e -> mostraRegole());//tanto non cambia
        menuHelp.add(regoleMenuItem);
        menuBar.add(menuHelp);

        frame.setJMenuBar(menuBar);

        // Inizializza la griglia delle celle
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(g.getDimension(), g.getDimension()));
        celle = new JTextField[g.getDimension()][g.getDimension()];
        labelTargets = new JLabel[g.getDimension()][g.getDimension()]; // Array per le JLabel

        
        for (int i = 0; i < g.getDimension(); i++) {
            for (int j = 0; j < g.getDimension(); j++) {
                JPanel cellPanel = new JPanel(new BorderLayout()); // Pannello per cella
                celle[i][j] = new JTextField();
                celle[i][j].setHorizontalAlignment(JTextField.CENTER);

                // Crea e configura la JLabel per il target e l'operazione
                labelTargets[i][j] = new JLabel();
                labelTargets[i][j].setHorizontalAlignment(SwingConstants.LEFT);
                labelTargets[i][j].setVerticalAlignment(SwingConstants.TOP);
                labelTargets[i][j].setFont(new Font("Arial", Font.PLAIN, 10)); // Imposta la dimensione del font

                // Aggiungi celle e JLabel al pannello della cella
                cellPanel.add(labelTargets[i][j], BorderLayout.NORTH);
                cellPanel.add(celle[i][j], BorderLayout.CENTER);

                // Aggiungi il pannello della cella al pannello della griglia
                gridPanel.add(cellPanel);
            }
        }
        aggiungiDocumentListner();
        // Inizializza i pulsanti
        JPanel buttonPanel = new JPanel();
        controllaVincoliButton = new JButton("Controlla Vincoli");
        controllaVincoliButton.addActionListener(this);
        risolviButton = new JButton("Risolvi");
        risolviButton.addActionListener(this);
        riprendiButton = new JButton("Riprendi");
        riprendiButton.setEnabled(false);
        riprendiButton.addActionListener(this);
        prevButton = new JButton("Prev");
        prevButton.addActionListener(this);
        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
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

    private void aggiungiDocumentListner() {
        for (int i = 0; i < g.getDimension(); i++) {
            for (int j = 0; j < g.getDimension(); j++) {
                JTextField textField = celle[i][j];
                textField.getDocument().addDocumentListener(new MyDocumentListner(i,j));
            }
        }
    }

    public void creaCageGrafici() {
        Map<Cage, LinkedList<Cell>> cageMap = g.getGeneratore().getCageCell();
        Set<Color> usedColors = new HashSet<>();
        List<Color> colorPalette = Arrays.asList(Color.GREEN,Color.MAGENTA,Color.CYAN,Color.ORANGE,Color.PINK,Color.GRAY,Color.DARK_GRAY,Color.white,Color.LIGHT_GRAY,Color.yellow);
        usedColors.add(Color.RED);
        Iterator<Color> colorIterator = colorPalette.iterator();
        Random rand = new Random();

        for (Map.Entry<Cage, LinkedList<Cell>> entry : cageMap.entrySet()) {
            LinkedList<Cell> cells = entry.getValue();
            Cage cage = entry.getKey();
            Color color;

            if (colorIterator.hasNext()) {
                color = colorIterator.next();
            } else {
                // Genera un colore casuale che non è rosso
                do {
                    color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
                } while (color.equals(Color.RED) || usedColors.contains(color));
            }

            usedColors.add(color);

            for (Cell cell : cells) {
                int x = cell.getX();
                int y = cell.getY();
                celle[x][y].setBackground(color);
                labelTargets[x][y].setText(cage.toString());
            }
        }
    }


    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }//setMEdiator


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == controllaVincoliButton){
            mediator.notify("ControllaVincoli");
        }
        else if(e.getSource() == risolviButton){
            memento = g.createMemento();
            mediator.notify("Risolvi");
            //System.out.println("stampa in finestra gioco" +Grid.getInstance());
        }
        else if(e.getSource() == riprendiButton){
            g.setMemento(memento);
            mediator.notify("Riprendi");
            //repaint();
            //revalidate();
        }
        else if(e.getSource()==nextButton){
            mediator.notify("NextSol");
        }
        else if(e.getSource()==prevButton){
            mediator.notify("PrevSol");
        }
        else if(e.getSource()==salvaPartitaMenuItem){
            mediator.notify("Salva");
        }
    }



    private void mostraRegole() {
        String regole = "KenKen è un gioco di logica con le seguenti regole:\n\n" +
                "1. Riempire la griglia con numeri da 1 a N (dove N è la dimensione della griglia).\n" +
                "2. Ogni numero deve apparire una sola volta per riga e per colonna.\n" +
                "3. La griglia è suddivisa in 'cages', ciascuno con un obiettivo aritmetico.\n" +
                "4. Raggiungere l'obiettivo aritmetico utilizzando i numeri all'interno della gabbia.\n" +
                "   - Operazioni possibili: addizione, sottrazione, moltiplicazione, divisione.\n" +
                "5. Esempio: se una gabbia ha l'obiettivo 6+, i numeri devono sommarsi a 6.\n\n" +
                "Il gioco è risolto quando tutti i numeri sono inseriti rispettando le regole di unicità per riga e colonna " +
                "e soddisfacendo gli obiettivi aritmetici di tutte le gabbie.";
        JOptionPane.showMessageDialog(frame, regole, "Regole di KenKen", JOptionPane.INFORMATION_MESSAGE);
    }//mostraRegole




    public JFrame getFrame(){
        return frame;
    }

    public JTextField[][] getCelle() {
        return celle;
    }

    public JLabel[][] getLabelTargets() {
        return labelTargets;
    }

    public JButton getRisolviButton() {
        return risolviButton;
    }

    public JButton getRiprendiButton() {
        return riprendiButton;
    }

    public JButton getPrevButton() {
        return prevButton;
    }

    public JButton getNextButton() {
        return nextButton;
    }

    public JButton getControllaVincoliButton(){
        return controllaVincoliButton;
    }


    private class MyDocumentListner implements DocumentListener{
        private int x;
        private int y;

        public MyDocumentListner(int x,int y){
            this.x=x;
            this.y=y;
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            updateGrid(x, y);
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            updateGrid(x, y);
        }

        @Override
        public void changedUpdate(DocumentEvent e){}

    }


    private void updateGrid(int x,int y) {
        String text = celle[x][y].getText();
        if (!text.isEmpty()) {
            try {
                int value = Integer.parseInt(text);
                if(value>0 && value<=g.getDimension()) {
                    g.addValue(value, x, y);
                }
                else{
                    JOptionPane.showMessageDialog(frame, "Inserisci un numero >0 e <dimensione.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                // Se il testo inserito non è un numero valido, gestisci l'eccezione qui
                // Ad esempio, mostra un messaggio di errore o reimposta il testo del JTextField
                JOptionPane.showMessageDialog(frame, "Inserisci un numero valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                // Reset del testo del JTextField
                celle[x][y].setText("");
            }
        }
        else{
            g.getCell(x,y).clean();
        }
    }




}//FinestraGioco

