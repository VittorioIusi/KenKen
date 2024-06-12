package main.grafica.command;

import main.backtracking.Risolutore;
import main.grafica.mediator.ConcreteMediator;
import main.griglia.componenti.Cell;
import main.griglia.componenti.Grid;

import javax.swing.*;
import java.util.ListIterator;

public class IteraSoluzioniCommand implements Command{
    private ConcreteMediator m;
    private String op;

    public IteraSoluzioniCommand(ConcreteMediator m, String s){
        this.m =m;
        this.op=s;
    }

    public void execute(){
        switch (op){
            case "Next":
                next();
                break;
            case "Prev":
                prev();
                break;
        }
    }//execute

    private void next(){
        Grid g = Grid.getInstance();
        Risolutore r = Risolutore.getInstance(g);
        Cell[][] next = r.nextSol();
        if(next != null){
            aggiornaGriglia(next);
            m.getFinestraGioco().getPrevButton().setEnabled(true);
        }
        else{
            JOptionPane.showMessageDialog(m.getFinestraGioco().getFrame(), "Soluzioni finite", "Alert", JOptionPane.ERROR_MESSAGE);
            m.getFinestraGioco().getNextButton().setEnabled(false);

        }

    }//next

    private void prev(){
        Grid g = Grid.getInstance();
        Risolutore r = Risolutore.getInstance(g);
        Cell[][] prev = r.prevSol();
        if(prev != null){
            aggiornaGriglia(prev);
            m.getFinestraGioco().getNextButton().setEnabled(true);
        }
        else{
            JOptionPane.showMessageDialog(m.getFinestraGioco().getFrame(), "Soluzioni finite", "Alert", JOptionPane.ERROR_MESSAGE);
            m.getFinestraGioco().getPrevButton().setEnabled(false);
        }
    }//prev

    private void aggiornaGriglia(Cell[][] g) {
        JTextField[][] c = m.getFinestraGioco().getCelle();
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
    }


}//iteraSoluzioniCommand
