package main.grafica.mediator;

import main.backtracking.Risolutore;
import main.grafica.PartitaSerializzata;
import main.grafica.pannelli.FinestraGioco;
import main.grafica.pannelli.FinestraIniziale;
import main.grafica.pannelli.FinestraSetting;
import main.griglia.componenti.Cage;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class ConcreteMediator implements Mediator {
    private static ConcreteMediator INSTANCE;
    private FinestraIniziale finestraIniziale;
    private FinestraSetting finestraSetting;
    private FinestraGioco finestraGioco;



    private ConcreteMediator(FinestraIniziale f) {
        this.finestraIniziale = f;
    }//costruttore


    public static synchronized ConcreteMediator getConcreteMediator(FinestraIniziale f){
        if(INSTANCE==null)
            INSTANCE=new ConcreteMediator(f);
        return INSTANCE;
    }//getConcreteMEdiator


    @Override
    public void notify(String sender) {
        switch (sender) {
            case "NuovaPartita":
                System.out.println("controllore nuova partita");
                nuovaPartita();
                break;
            case "CaricaPartita":
                System.out.println("controllore carica partita");
                caricaPartita();
                break;
            case "ConfermaImpostazioni":
                System.out.println("controllore conferma impostazioni");
                confermaImpostazioni();
                break;
            case "Risolvi":
                System.out.println("controllore risolvi");
                risolvi();
                break;
            case "Riprendi":
                System.out.println("controllore riprendi");
                riprendi();
                break;
            case "NextSol":
                System.out.println("controllore nextSol");
                nextSol();
                break;
            case "PrevSol":
                System.out.println("controllore prevSol");
                prevSol();
                break;
            case "ControllaVincoli":
                System.out.println("controllore controllaVincoli");
                controllaVincoli();
                break;
            case "Salva":
                System.out.println("controllore salva");
                salva();
        }//switch

    }//notify



    public void nuovaPartita() {
        FinestraSetting f = new FinestraSetting();
        finestraSetting = f;
        finestraSetting.getFrame().setVisible(true);
        //finestraIniziale.getFrame().setVisible(false);
        finestraIniziale.getFrame().dispose();//elimino la finestra invece di nasconderla
        finestraSetting.setMediator(this);
    }//nuovaPartita


    public void caricaPartita(){
        Grid g = Grid.getInstance();
        String nomeFile = null;
        String absolutePath = null;
        JFileChooser jfc = new JFileChooser();
        int val = jfc.showOpenDialog(null);
        if(val==JFileChooser.APPROVE_OPTION) {
            absolutePath = jfc.getSelectedFile().getAbsolutePath();
            nomeFile = jfc.getSelectedFile().getName();
            JOptionPane.showMessageDialog(null,"Carica partita da: "+nomeFile);
        }
        try {
            PartitaSerializzata read = null;
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(absolutePath));
            while(true) {
                try {
                    read = (PartitaSerializzata) oos.readObject();
                    System.out.println("letto file");
                    System.out.println(read.getDim());
                    g.setDimension(read.getDim());
                    g.cambiaRiferimento(read.getGrid());
                    System.out.println(g);
                }catch(EOFException e1){
                    break;
                }
            }
            oos.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e2) {
            e2.printStackTrace();
        } catch (ClassCastException e3) {
            e3.printStackTrace();
        }
        // Mostra la finestra di gioco e aggiorna i valori sulla griglia
        FinestraGioco fG = new FinestraGioco();
        finestraGioco = fG;
        colora();
        //finestraIniziale.getFrame().setVisible(false);
        finestraIniziale.getFrame().dispose();
        finestraGioco.getFrame().setVisible(true);
        finestraGioco.getRiprendiButton().setEnabled(false);
        finestraGioco.getNextButton().setEnabled(false);
        finestraGioco.getPrevButton().setEnabled(false);
        finestraGioco.getRiprendiButton().setEnabled(false);
        finestraGioco.setMediator(this);
        aggiornaValoriRiprendi();
    }//caricaPartita



    public void confermaImpostazioni(){
        Grid g = Grid.getInstance();
        int dim = Integer.parseInt(finestraSetting.getDimensioneGrigliaText().getText());
        System.out.println("Dimensione griglia: " + dim);
        int sol = Integer.parseInt(finestraSetting.getNumSolBacktrackingText().getText());
        System.out.println("Num Sol: " + sol);
        g.setDimension(dim);
        g.getGeneratore().genera();
        g.getRisolutore().setMaxSol(sol);
        FinestraGioco fG = new FinestraGioco();
        finestraGioco = fG;
        //finestraSetting.getFrame().setVisible(false);
        finestraSetting.getFrame().dispose();
        finestraGioco.getFrame().setVisible(true);
        finestraGioco.getRiprendiButton().setEnabled(false);
        finestraGioco.getNextButton().setEnabled(false);
        finestraGioco.getPrevButton().setEnabled(false);
        finestraGioco.getRiprendiButton().setEnabled(false);
        finestraGioco.setMediator(this);
    }//confermaImpostazioni


    public void risolvi(){
        Risolutore risolutore = Risolutore.getInstance(Grid.getInstance());
        risolutore.risolvi();
        finestraGioco.getNextButton().setEnabled(true);
        finestraGioco.getPrevButton().setEnabled(true);
        finestraGioco.getRiprendiButton().setEnabled(true);
        finestraGioco.getRisolviButton().setEnabled(false);
        aggiornaValori();
    }//risolvi


    public void riprendi(){
        aggiornaValoriRiprendi();
        finestraGioco.getRisolviButton().setEnabled(true);
        finestraGioco.getRiprendiButton().setEnabled(false);
        finestraGioco.getPrevButton().setEnabled(false);
        finestraGioco.getNextButton().setEnabled(false);
    }//riprendi


    public void nextSol(){
        Grid g = Grid.getInstance();
        Risolutore r = Risolutore.getInstance(g);
        Cell[][] next = r.nextSol();
        if(next != null){
            aggiornaGriglia(next);
            finestraGioco.getPrevButton().setEnabled(true);
        }
        else{
            JOptionPane.showMessageDialog(finestraGioco.getFrame(), "Soluzioni finite", "Alert", JOptionPane.ERROR_MESSAGE);
            finestraGioco.getNextButton().setEnabled(false);

        }
    }//nextSol


    public void prevSol(){
        Grid g = Grid.getInstance();
        Risolutore r = Risolutore.getInstance(g);
        Cell[][] prev = r.prevSol();
        if(prev != null){
            aggiornaGriglia(prev);
            finestraGioco.getNextButton().setEnabled(true);
        }
        else{
            JOptionPane.showMessageDialog(finestraGioco.getFrame(), "Soluzioni finite", "Alert", JOptionPane.ERROR_MESSAGE);
            finestraGioco.getPrevButton().setEnabled(false);
        }
    }//prevSol


    public void controllaVincoli(){
        JTextField[][] text = finestraGioco.getCelle();
        Grid g = Grid.getInstance();
        if(g.isCompleted()){
            finestraGioco.getControllaVincoliButton().setBackground(Color.GREEN);
        }
        else{
            finestraGioco.getControllaVincoliButton().setBackground(Color.RED);
        }
        for(int i=0;i<g.getDimension();i++){
            for(int j=0;j< g.getDimension();j++){
                List<Cell> contrast = g.getCell(i,j).getInContrast();
                if(!contrast.isEmpty() && g.getCell(i,j).getValue()!=0){
                    text[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 5));
                }
                else{
                    text[i][j].setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                }
            }
        }
    }//controllaVincoli



    public void salva(){
        Grid g = Grid.getInstance();
        String nomeFile = null;
        String absolutePath = null;
        JFileChooser jfc = new JFileChooser();
        int val = jfc.showOpenDialog(null);
        if(val==JFileChooser.APPROVE_OPTION) {
            absolutePath = jfc.getSelectedFile().getAbsolutePath();
            nomeFile = jfc.getSelectedFile().getName();
            JOptionPane.showMessageDialog(null,"File salvato: "+nomeFile);
        }
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(absolutePath));
            PartitaSerializzata info = new PartitaSerializzata(g.getDimension(), g.getGridSerializzazione());
            oos.writeObject(info);
            oos.flush();
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//salva


    private void aggiornaValori() {
        JTextField[][] c = finestraGioco.getCelle();
        Cell[][] g = Risolutore.getInstance(Grid.getInstance()).getSol().get(0);

        for(int i=0; i<g.length; i++){
            for(int j=0; j<g.length; j++){
                int value = g[i][j].getValue();
                if(value==0){
                    c[i][j].setText("");
                }
                else
                    c[i][j].setText(Integer.toString(value));
            }
        }
    }//aggiornaValori


    private void aggiornaValoriRiprendi() {
        JTextField[][] c = finestraGioco.getCelle();
        Grid g = Grid.getInstance();
        for(int i=0; i<g.getDimension(); i++){
            for(int j=0; j<g.getDimension(); j++){
                int value = g.getValue(i,j);
                if(value==0){
                    c[i][j].setText("");
                }
                else
                    c[i][j].setText(Integer.toString(value));
            }
        }
    }//aggiornaValoriRiprendi


    private void aggiornaGriglia(Cell[][] g) {
        JTextField[][] c = finestraGioco.getCelle();
        Grid grid = Grid.getInstance();
        for(int i=0; i<g.length; i++){
            for(int j=0; j<g.length; j++){
                int value = g[i][j].getValue();
                if(value==0){
                    c[i][j].setText("");
                }
                else
                    grid.addValue(value,i,j);
                c[i][j].setText(Integer.toString(value));
            }
        }
    }//aggiornaGriglia



    public void colora(){
        Grid g = Grid.getInstance();
        Map<Cage, LinkedList<Cell>> list = new HashMap<>();
        for(int i=0; i<g.getDimension(); i++){
            for(int j=0; j<g.getDimension(); j++){
                Cage c = g.getCell(i,j).getConstraint();
                if(!list.keySet().contains(g.getCell(i,j).getConstraint())){
                    list.put(c,c.getCells());
                }
            }
        }
        Set<Color> usedColors = new HashSet<>();
        List<Color> colorPalette = Arrays.asList(Color.GREEN,Color.MAGENTA,Color.CYAN,Color.ORANGE,Color.PINK,Color.GRAY,Color.DARK_GRAY,Color.white,Color.LIGHT_GRAY,Color.yellow);
        usedColors.add(Color.RED);
        Iterator<Color> colorIterator = colorPalette.iterator();
        Random rand = new Random();

        for (Map.Entry<Cage, LinkedList<Cell>> entry : list.entrySet()) {
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
                finestraGioco.getCelle()[x][y].setBackground(color);
                finestraGioco.getLabelTargets()[x][y].setText(cage.toString());
            }
        }
    }//colora


}//ConcreteMediator
