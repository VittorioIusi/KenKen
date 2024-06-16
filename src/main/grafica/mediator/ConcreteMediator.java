package main.grafica.mediator;

import main.backtracking.Risolutore;
import main.grafica.pannelli.FinestraGioco;
import main.grafica.pannelli.FinestraIniziale;
import main.grafica.pannelli.FinestraSetting;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Carica Partita");

        int userSelection = fileChooser.showOpenDialog(finestraIniziale.getFrame());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            try (Scanner scanner = new Scanner(new File(filePath))) {
                // Leggi la dimensione della griglia e il numero di soluzioni
                int dimension = scanner.nextInt();
                int numSol = scanner.nextInt();

                // Imposta la dimensione della griglia e il numero di soluzioni
                Grid g = Grid.getInstance();
                g.setDimension(dimension);
                g.getGeneratore().genera();
                g.getRisolutore().setMaxSol(numSol);

                // Leggi la griglia di gioco
                for (int i = 0; i < dimension; i++) {
                    for (int j = 0; j < dimension; j++) {
                        int value = scanner.nextInt();
                        g.addValue(value, i, j);
                    }
                }

                // Mostra la finestra di gioco e aggiorna i valori sulla griglia
                FinestraGioco fG = new FinestraGioco();
                finestraGioco = fG;
                //finestraIniziale.getFrame().setVisible(false);
                finestraIniziale.getFrame().dispose();
                finestraGioco.getFrame().setVisible(true);
                finestraGioco.getRiprendiButton().setEnabled(false);
                finestraGioco.getNextButton().setEnabled(false);
                finestraGioco.getPrevButton().setEnabled(false);
                finestraGioco.getRiprendiButton().setEnabled(false);
                finestraGioco.setMediator(this);
                aggiornaValoriRiprendi();

                JOptionPane.showMessageDialog(finestraGioco.getFrame(), "Partita caricata correttamente da " + filePath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(finestraIniziale.getFrame(), "Errore durante il caricamento della partita", "Errore", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salva Partita");

        int userSelection = fileChooser.showSaveDialog(finestraGioco.getFrame());

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            if (!filePath.toLowerCase().endsWith(".txt")) {
                filePath += ".txt";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                // Salva i dati della partita
                writer.write(Grid.getInstance().getDimension() + "\n");
                writer.write(Risolutore.getInstance(Grid.getInstance()).getMaxSol() + "\n");
                // Scrivi la griglia
                for (int i = 0; i < Grid.getInstance().getDimension(); i++) {
                    for (int j = 0; j < Grid.getInstance().getDimension(); j++) {
                        writer.write(Grid.getInstance().getValue(i, j) + " ");
                    }
                    writer.write("\n");
                }

                JOptionPane.showMessageDialog(finestraGioco.getFrame(), "Partita salvata correttamente in " + filePath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(finestraGioco.getFrame(), "Errore durante il salvataggio della partita", "Errore", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
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



}//ConcreteMediator
